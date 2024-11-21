package com.damoralesr97.devsu.cuenta_movimiento_service.service;

import com.damoralesr97.devsu.cuenta_movimiento_service.dto.account.event.EventAccountRequest;
import com.damoralesr97.devsu.cuenta_movimiento_service.dto.client.response.ClientResponse;
import com.damoralesr97.devsu.cuenta_movimiento_service.model.Movement;
import com.damoralesr97.devsu.cuenta_movimiento_service.service.interfaces.IAccountService;
import com.damoralesr97.devsu.cuenta_movimiento_service.utils.ClientRestConsumer;
import com.damoralesr97.devsu.cuenta_movimiento_service.utils.enums.MovementTypeEnum;
import com.damoralesr97.devsu.cuenta_movimiento_service.utils.exceptions.AlreadyExistsException;
import com.damoralesr97.devsu.cuenta_movimiento_service.utils.exceptions.NotFoundExcepcion;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.stereotype.Service;
import com.damoralesr97.devsu.cuenta_movimiento_service.repository.AccountRepository;
import com.damoralesr97.devsu.cuenta_movimiento_service.mapper.AccountMapper;
import com.damoralesr97.devsu.cuenta_movimiento_service.model.Account;
import com.damoralesr97.devsu.cuenta_movimiento_service.dto.account.request.AccountRequest; 
import com.damoralesr97.devsu.cuenta_movimiento_service.dto.account.response.AccountResponse;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements IAccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final ClientRestConsumer clientRestConsumer;

    @Override
    public List<AccountResponse> findAll() {
        return accountRepository.findAll().stream().map(accountMapper::toResponse).toList();
    }

    @Override
    public Optional<AccountResponse> findByAccountNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber).map(accountMapper::toResponse);
    }

    @Override
    public AccountResponse save(AccountRequest request) {
        Optional<ClientResponse> clientResponse = clientRestConsumer.findClientByDni(request.getClientDni());
        if (clientResponse.isEmpty() || BooleanUtils.isFalse(clientResponse.get().getStatus())) {
            throw new NotFoundExcepcion("Client not found with dni " + request.getClientDni());
        }
        Optional<AccountResponse> existAccount = findByAccountNumber(request.getAccountNumber());
        if (existAccount.isPresent()) {
            throw new AlreadyExistsException("Account already exists with account number " + existAccount.get().getAccountNumber());
        }
        Account account = accountMapper.toEntity(request);
        Movement movement = Movement.builder()
                .movementDate(new Timestamp(System.currentTimeMillis()))
                .movementType(MovementTypeEnum.DEPOSIT)
                .value(account.getInitialBalance())
                .balance(account.getInitialBalance())
                .account(account)
                .build();
        account.setClientId(clientResponse.get().getId());
        account.setMovements(List.of(movement));
        account.setStatus(Boolean.TRUE);
        return accountMapper.toResponse(accountRepository.save(account));
    }

    @Override
    public void save(EventAccountRequest request) {
        Optional<AccountResponse> existAccount = findByAccountNumber(request.getAccountNumber());
        if (existAccount.isPresent()) {
            throw new AlreadyExistsException("Account already exists with account number " + existAccount.get().getAccountNumber());
        }
        Account account = accountMapper.eventRequestToEntity(request);
        Movement movement = Movement.builder()
                .movementDate(new Timestamp(System.currentTimeMillis()))
                .movementType(MovementTypeEnum.DEPOSIT)
                .value(account.getInitialBalance())
                .balance(account.getInitialBalance())
                .account(account)
                .build();
        account.setClientId(request.getClientId());
        account.setMovements(List.of(movement));
        account.setStatus(Boolean.TRUE);
        accountRepository.save(account);
    }

    @Override
    public AccountResponse update(String accountNumber, AccountRequest request) {
        Account existingEntity = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new NotFoundExcepcion("Account not found with account number " + accountNumber));
        
        accountMapper.updateFromDto(request, existingEntity);
        
        Account savedEntity = accountRepository.save(existingEntity);
        return accountMapper.toResponse(savedEntity);
    }

    @Override
    public void delete(String accountNumber) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                        .orElseThrow(() -> new NotFoundExcepcion("Account not found with account number " + accountNumber));
        account.setStatus(Boolean.FALSE);
        accountRepository.save(account);
    }

    @Override
    @Transactional
    public List<AccountResponse> findByClientId(Long clientId) {
        return accountRepository.findByClientId(clientId).stream().map(accountMapper::toResponse).toList();
    }

}
package com.damoralesr97.devsu.cuenta_movimiento_service.service;

import com.damoralesr97.devsu.cuenta_movimiento_service.dto.account.response.AccountResponse;
import com.damoralesr97.devsu.cuenta_movimiento_service.mapper.AccountMapper;
import com.damoralesr97.devsu.cuenta_movimiento_service.model.Account;
import com.damoralesr97.devsu.cuenta_movimiento_service.service.interfaces.IAccountService;
import com.damoralesr97.devsu.cuenta_movimiento_service.service.interfaces.IMovementService;
import com.damoralesr97.devsu.cuenta_movimiento_service.utils.enums.MovementTypeEnum;
import com.damoralesr97.devsu.cuenta_movimiento_service.utils.exceptions.MovementExcepcion;
import com.damoralesr97.devsu.cuenta_movimiento_service.utils.exceptions.NotFoundExcepcion;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.stereotype.Service;
import com.damoralesr97.devsu.cuenta_movimiento_service.repository.MovementRepository;
import com.damoralesr97.devsu.cuenta_movimiento_service.mapper.MovementMapper;
import com.damoralesr97.devsu.cuenta_movimiento_service.model.Movement;
import com.damoralesr97.devsu.cuenta_movimiento_service.dto.movement.request.MovementRequest; 
import com.damoralesr97.devsu.cuenta_movimiento_service.dto.movement.response.MovementResponse;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MovementServiceImpl implements IMovementService {

    private final MovementRepository movementRepository;
    private final IAccountService accountService;
    private final MovementMapper movementMapper;
    private final AccountMapper accountMapper;

    @Override
    public List<MovementResponse> findAll() {
        return movementRepository.findAll().stream().map(movementMapper::toResponse).toList();
    }

    @Override
    public Optional<MovementResponse> findById(Long id) {
        return movementRepository.findById(id).map(movementMapper::toResponse);
    }

    @Override
    public MovementResponse save(MovementRequest request) {
        MovementTypeEnum movementType = getMovementType(request.getValue());
        Optional<AccountResponse> account = accountService.findByAccountNumber(request.getAccountNumber());
        if (account.isEmpty() || BooleanUtils.isFalse(account.get().getStatus())) {
            throw new NotFoundExcepcion("Account " + request.getAccountNumber() + " not found.");
        }
        Account accountEntity = accountMapper.toEntityFromResponse(account.get());
        if (MovementTypeEnum.WITHDRAWAL == movementType) {
            if (!hasSufficientBalance(accountEntity.calculateBalance(), request.getValue())) {
                throw new MovementExcepcion("Account " + accountEntity.getAccountNumber() + " has insufficient balance");
            }
        }
        Movement movement = movementMapper.toEntity(request);
        movement.setMovementDate(new Timestamp(System.currentTimeMillis()));
        movement.setMovementType(movementType);
        movement.setBalance(accountEntity.calculateBalance().add(movement.getValue()));
        movement.setAccount(accountEntity);
        return movementMapper.toResponse(movementRepository.save(movement));
    }

    @Override
    public MovementResponse update(Long id, MovementRequest request) {
        Movement existingEntity = movementRepository.findById(id)
                .orElseThrow(() -> new NotFoundExcepcion("Movement not found with id " + id));
        
        movementMapper.updateFromDto(request, existingEntity);
        
        Movement savedEntity = movementRepository.save(existingEntity);
        return movementMapper.toResponse(savedEntity);
    }

    @Override
    public void delete(Long id) {
        movementRepository.deleteById(id);
    }

    private MovementTypeEnum getMovementType(BigDecimal value) {
        if (value.signum() > 0) {
            return MovementTypeEnum.DEPOSIT;
        } else if (value.signum() < 0) {
            return MovementTypeEnum.WITHDRAWAL;
        } else {
            throw new MovementExcepcion("You cannot perform a movement with value 0");
        }
    }

    private Boolean hasSufficientBalance(BigDecimal balance, BigDecimal value) {
        if (value.abs().compareTo(balance) > 0) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

}
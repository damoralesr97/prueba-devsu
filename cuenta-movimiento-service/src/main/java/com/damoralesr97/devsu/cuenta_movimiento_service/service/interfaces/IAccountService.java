package com.damoralesr97.devsu.cuenta_movimiento_service.service.interfaces;

import com.damoralesr97.devsu.cuenta_movimiento_service.dto.account.event.EventAccountRequest;
import com.damoralesr97.devsu.cuenta_movimiento_service.dto.account.request.AccountRequest;
import com.damoralesr97.devsu.cuenta_movimiento_service.dto.account.response.AccountResponse;

import java.util.List;
import java.util.Optional;

public interface IAccountService {

    List<AccountResponse> findAll();
    Optional<AccountResponse> findByAccountNumber(String accountNumber);
    AccountResponse save(AccountRequest request);
    void save(EventAccountRequest request);
    AccountResponse update(String accountNumber, AccountRequest request);
    void delete(String accountNumber);
    List<AccountResponse> findByClientId(Long clientId);

}

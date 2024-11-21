package com.damoralesr97.devsu.cuenta_movimiento_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.damoralesr97.devsu.cuenta_movimiento_service.model.Account;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByAccountNumber(String accountNumber);

    List<Account> findByClientId(Long clientId);

}
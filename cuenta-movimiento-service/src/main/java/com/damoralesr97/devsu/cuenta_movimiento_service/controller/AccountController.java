package com.damoralesr97.devsu.cuenta_movimiento_service.controller;

import com.damoralesr97.devsu.cuenta_movimiento_service.service.interfaces.IAccountService;
import com.damoralesr97.devsu.cuenta_movimiento_service.utils.exceptions.NotFoundExcepcion;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.damoralesr97.devsu.cuenta_movimiento_service.dto.account.request.AccountRequest;
import com.damoralesr97.devsu.cuenta_movimiento_service.dto.account.response.AccountResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cuentas")
public class AccountController {

    private final IAccountService accountService;

    @GetMapping
    public ResponseEntity<?> findAll() {
        List<AccountResponse> accounts = accountService.findAll();
        if (accounts.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(accounts);
    }

    @GetMapping("/{accountNumber}")
    public ResponseEntity<?> findByAccountNumber(@PathVariable String accountNumber) {
        return accountService.findByAccountNumber(accountNumber)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new NotFoundExcepcion("Account " + accountNumber + " not found"));
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody AccountRequest request) {
        AccountResponse savedAccount = accountService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAccount);
    }

    @PutMapping("/{accountNumber}")
    public ResponseEntity<?> update(@PathVariable String accountNumber, @RequestBody AccountRequest request) {
        AccountResponse updatedAccount = accountService.update(accountNumber, request);
        return ResponseEntity.ok(updatedAccount);
    }

    @DeleteMapping("/{accountNumber}")
    public ResponseEntity<?> delete(@PathVariable String accountNumber) {
        accountService.delete(accountNumber);
        return ResponseEntity.ok().build();
    }

}

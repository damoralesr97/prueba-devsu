package com.damoralesr97.devsu.cuenta_movimiento_service.controller;

import com.damoralesr97.devsu.cuenta_movimiento_service.dto.account.response.AccountResponse;
import com.damoralesr97.devsu.cuenta_movimiento_service.service.interfaces.IAccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AccountController.class)
public class AccountControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IAccountService accountService;

    @Test
    public void testFindByAccountNumber_Success() throws Exception {
        String accountNumber = "12345";
        AccountResponse accountResponse = AccountResponse.builder()
                .accountNumber(accountNumber)
                .build();

        given(accountService.findByAccountNumber(accountNumber)).willReturn(Optional.of(accountResponse));

        mockMvc.perform(get("/cuentas/{accountNumber}", accountNumber))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountNumber").value(accountNumber));
    }

    @Test
    public void testFindByAccountNumber_NotFound() throws Exception {
        String accountNumber = "12345";

        given(accountService.findByAccountNumber(accountNumber)).willReturn(Optional.empty());

        mockMvc.perform(get("/cuentas/{accountNumber}", accountNumber))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Account " + accountNumber + " not found"));
    }
}


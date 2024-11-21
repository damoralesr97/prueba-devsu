package com.damoralesr97.devsu.cuenta_movimiento_service.dto.account.request;

import com.damoralesr97.devsu.cuenta_movimiento_service.utils.enums.AccountTypeEnum;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountRequest {

    private String accountNumber;
    private AccountTypeEnum typeAccount;
    private BigDecimal initialBalance;
    private String clientDni;

}

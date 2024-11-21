package com.damoralesr97.devsu.cuenta_movimiento_service.dto.account.event;

import com.damoralesr97.devsu.cuenta_movimiento_service.utils.enums.AccountTypeEnum;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class EventAccountRequest {

    private String accountNumber;
    private AccountTypeEnum typeAccount;
    private BigDecimal initialBalance;
    private Long clientId;

}

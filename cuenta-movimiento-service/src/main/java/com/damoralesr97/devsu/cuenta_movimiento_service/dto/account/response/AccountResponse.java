package com.damoralesr97.devsu.cuenta_movimiento_service.dto.account.response;

import com.damoralesr97.devsu.cuenta_movimiento_service.model.Movement;
import com.damoralesr97.devsu.cuenta_movimiento_service.utils.enums.AccountTypeEnum;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class AccountResponse {

    private Long id;
    private String accountNumber;
    private AccountTypeEnum typeAccount;
    private BigDecimal initialBalance;
    private Boolean status;
    private Long clientId;

}

package com.damoralesr97.devsu.cliente_persona_service.dto.account.event;

import com.damoralesr97.devsu.cliente_persona_service.utils.enums.AccountTypeEnum;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder(toBuilder = true)
public class EventAccountRequest {

    private String accountNumber;
    private AccountTypeEnum typeAccount;
    private BigDecimal initialBalance;
    private Long clientId;

}

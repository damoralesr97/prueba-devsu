package com.damoralesr97.devsu.cliente_persona_service.dto.client.request;

import com.damoralesr97.devsu.cliente_persona_service.utils.enums.AccountTypeEnum;
import com.damoralesr97.devsu.cliente_persona_service.utils.enums.GenreEnum;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ClientAccountRequest {

    private String name;
    private GenreEnum genre;
    private int age;
    private String dni;
    private String address;
    private String phoneNumber;
    private String password;
    private String accountNumber;
    private AccountTypeEnum typeAccount;
    private BigDecimal initialBalance;

}

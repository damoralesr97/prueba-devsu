package com.damoralesr97.devsu.cuenta_movimiento_service.dto.report.response;

import com.damoralesr97.devsu.cuenta_movimiento_service.utils.enums.AccountTypeEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class ReportResponse {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "America/Guayaquil")
    private Date movementDate;
    private String client;
    private String accountNumber;
    private AccountTypeEnum accountType;
    private BigDecimal initialBalance;
    private Boolean status;
    private BigDecimal movement;
    private BigDecimal availableBalance;

}

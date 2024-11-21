package com.damoralesr97.devsu.cuenta_movimiento_service.dto.movement.response;

import java.sql.Timestamp;
import com.damoralesr97.devsu.cuenta_movimiento_service.utils.enums.MovementTypeEnum;
import java.math.BigDecimal;
import com.damoralesr97.devsu.cuenta_movimiento_service.model.Account;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
public class MovementResponse {

    private Long id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "America/Guayaquil")
    private Timestamp movementDate;
    private MovementTypeEnum movementType;
    private BigDecimal value;
    private BigDecimal balance;
    private Account account;

}
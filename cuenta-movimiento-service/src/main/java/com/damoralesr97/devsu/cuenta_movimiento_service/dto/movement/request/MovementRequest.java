package com.damoralesr97.devsu.cuenta_movimiento_service.dto.movement.request;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class MovementRequest {

    private String accountNumber;
    private BigDecimal value;

}
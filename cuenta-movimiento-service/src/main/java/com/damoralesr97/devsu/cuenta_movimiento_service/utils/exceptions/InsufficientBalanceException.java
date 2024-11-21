package com.damoralesr97.devsu.cuenta_movimiento_service.utils.exceptions;

public class InsufficientBalanceException extends RuntimeException {

    public InsufficientBalanceException(String message) {
        super(message);
    }

}

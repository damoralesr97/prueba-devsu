package com.damoralesr97.devsu.cliente_persona_service.dto.exception;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ErrorDTO {

    private int code;
    private String error;
    private String message;
    private LocalDateTime timestamp;

}

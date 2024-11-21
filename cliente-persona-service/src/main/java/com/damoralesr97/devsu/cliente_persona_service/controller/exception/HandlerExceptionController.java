package com.damoralesr97.devsu.cliente_persona_service.controller.exception;

import com.damoralesr97.devsu.cliente_persona_service.dto.exception.ErrorDTO;
import com.damoralesr97.devsu.cliente_persona_service.utils.exceptions.AlreadyExistsException;
import com.damoralesr97.devsu.cliente_persona_service.utils.exceptions.NotFoundExcepcion;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class HandlerExceptionController {

    @ExceptionHandler({NotFoundExcepcion.class})
    public ResponseEntity<ErrorDTO> notFoundException(Exception ex) {
        ErrorDTO errorDTO = ErrorDTO.builder()
                .code(HttpStatus.NOT_FOUND.value())
                .error("Not found")
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDTO);
    }

    @ExceptionHandler({AlreadyExistsException.class})
    public ResponseEntity<ErrorDTO> alreadyExistsException(Exception ex) {
        ErrorDTO errorDTO = ErrorDTO.builder()
                .code(HttpStatus.CONFLICT.value())
                .error("Already exists")
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorDTO);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorDTO> constraintViolationException(ConstraintViolationException ex) {
        ErrorDTO errorDTO = ErrorDTO.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .error("Invalid input data")
                .message(ex.getConstraintViolations().stream()
                        .map(ConstraintViolation::getMessage)
                        .collect(Collectors.joining(", ")))
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO);
    }

}

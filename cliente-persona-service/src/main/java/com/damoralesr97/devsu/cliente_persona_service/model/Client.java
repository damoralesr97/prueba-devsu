package com.damoralesr97.devsu.cliente_persona_service.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(name = "T_CLIENTS")
public class Client extends Person {

    @Column(name = "CLI_PASSWORD", nullable = false)
    @NotNull(message = "La contrasena es requerida")
    private String password;

    @Column(name = "CLI_STATUS", nullable = false)
    private Boolean status;

}

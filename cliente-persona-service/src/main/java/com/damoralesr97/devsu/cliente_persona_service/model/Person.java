package com.damoralesr97.devsu.cliente_persona_service.model;

import com.damoralesr97.devsu.cliente_persona_service.utils.enums.GenreEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "T_PERSONS")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PER_ID", unique = true, nullable = false)
    private Long id;

    @Column(name = "PER_NAME", nullable = false)
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "El nombre solo debe contener letras")
    @NotNull(message = "El nombre es requerido")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "PER_GENRE", nullable = false)
    @NotNull(message = "El genero es requerido")
    private GenreEnum genre;

    @Column(name = "PER_AGE", nullable = false)
    @Min(value = 18,message = "La edad debe ser minima de 18")
    @NotNull(message = "La edad es requerida")
    private int age;

    @Column(name = "PER_DNI", unique = true, nullable = false, updatable = false)
    @Size(min = 10, max = 10, message = "La cedula debe tener 10 digitos")
    @NotNull(message = "El numero de identificacion es requerido")
    private String dni;

    @Column(name = "PER_ADDRESS", nullable = false)
    @NotNull(message = "La direccion es requerida")
    private String address;

    @Column(name = "PER_PHONE", nullable = false)
    @Pattern(regexp = "[0-9]+", message = "El telefono solo debe contener numeros")
    @NotNull(message = "El telefono es requerido")
    private String phoneNumber;

}

package com.damoralesr97.devsu.cliente_persona_service.model;

import static org.junit.jupiter.api.Assertions.*;

import com.damoralesr97.devsu.cliente_persona_service.utils.enums.GenreEnum;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;
import java.util.Set;

public class ClientTest {

    private Validator validator;

    public ClientTest() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidClient() {
        Client client = new Client();
        client.setName("Juan Perez");
        client.setGenre(GenreEnum.M);
        client.setAge(30);
        client.setDni("1234567890");
        client.setAddress("Av. 10 de Agosto, Quito");
        client.setPhoneNumber("0987654321");
        client.setPassword("securePassword123");
        client.setStatus(true);

        Set<ConstraintViolation<Client>> violations = validator.validate(client);

        assertTrue(violations.isEmpty(), "No debe haber violaciones de validación");
    }

    @Test
    public void testInvalidClientWithoutName() {
        Client client = new Client();
        client.setGenre(GenreEnum.F);
        client.setAge(30);
        client.setDni("1234567890");
        client.setAddress("Av. 10 de Agosto, Quito");
        client.setPhoneNumber("0987654321");
        client.setPassword("securePassword123");
        client.setStatus(true);

        Set<ConstraintViolation<Client>> violations = validator.validate(client);

        assertFalse(violations.isEmpty(), "Debe haber violaciones de validación");
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("El nombre es requerido")),
                "Debe fallar la validación para el nombre");
    }

    @Test
    public void testInvalidClientWithShortDni() {
        Client client = new Client();
        client.setName("Juan Pérez");
        client.setGenre(GenreEnum.F);
        client.setAge(30);
        client.setDni("12345");
        client.setAddress("Av. 10 de Agosto, Quito");
        client.setPhoneNumber("0987654321");
        client.setPassword("securePassword123");
        client.setStatus(true);

        Set<ConstraintViolation<Client>> violations = validator.validate(client);

        assertFalse(violations.isEmpty(), "Debe haber violaciones de validación");
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("La cedula debe tener 10 digitos")),
                "Debe fallar la validación para el DNI");
    }

    @Test
    public void testInvalidClientWithInvalidPhoneNumber() {
        Client client = new Client();
        client.setName("Juan Pérez");
        client.setGenre(GenreEnum.F);
        client.setAge(30);
        client.setDni("1234567890");
        client.setAddress("Av. 10 de Agosto, Quito");
        client.setPhoneNumber("ABCDEF");
        client.setPassword("securePassword123");
        client.setStatus(true);

        Set<ConstraintViolation<Client>> violations = validator.validate(client);

        assertFalse(violations.isEmpty(), "Debe haber violaciones de validación");
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("El telefono solo debe contener numeros")),
                "Debe fallar la validación para el teléfono");
    }
}

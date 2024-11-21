package com.damoralesr97.devsu.cuenta_movimiento_service.utils;

import com.damoralesr97.devsu.cuenta_movimiento_service.dto.client.response.ClientResponse;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

public class ClientRestConsumer {

    private static final RestTemplate restTemplate = new RestTemplate();

    public static Optional<ClientResponse> findClientByDni(String dni) {
        try {
            ResponseEntity<ClientResponse> clientResponse = restTemplate.exchange(
                    "http://localhost:8080/clientes/"+dni,
                    HttpMethod.GET,
                    null,
                    ClientResponse.class
            );
            return Optional.ofNullable(clientResponse.getBody());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

}

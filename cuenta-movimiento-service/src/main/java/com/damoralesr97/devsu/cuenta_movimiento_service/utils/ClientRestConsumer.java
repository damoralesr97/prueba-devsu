package com.damoralesr97.devsu.cuenta_movimiento_service.utils;

import com.damoralesr97.devsu.cuenta_movimiento_service.dto.client.response.ClientResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ClientRestConsumer {

    private final Environment env;

    private static final RestTemplate restTemplate = new RestTemplate();

    public Optional<ClientResponse> findClientByDni(String dni) {
        try {
            ResponseEntity<ClientResponse> clientResponse = restTemplate.exchange(
                    "http://" + env.getProperty("CLIENTS_API") + "/clientes/"+dni,
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

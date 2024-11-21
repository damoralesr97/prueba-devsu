package com.damoralesr97.devsu.cliente_persona_service.service.interfaces;

import com.damoralesr97.devsu.cliente_persona_service.dto.client.request.ClientAccountRequest;
import com.damoralesr97.devsu.cliente_persona_service.dto.client.request.ClientRequest;
import com.damoralesr97.devsu.cliente_persona_service.dto.client.response.ClientResponse;

import java.util.List;
import java.util.Optional;

public interface IClientService {

    List<ClientResponse> findAll();
    Optional<ClientResponse> findByDni(String dni);
    ClientResponse save(ClientAccountRequest request);
    ClientResponse update(String dni, ClientRequest request);
    void delete(String dni);
}

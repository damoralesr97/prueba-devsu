package com.damoralesr97.devsu.cliente_persona_service.service;

import com.damoralesr97.devsu.cliente_persona_service.dto.account.event.EventAccountRequest;
import com.damoralesr97.devsu.cliente_persona_service.dto.client.request.ClientAccountRequest;
import com.damoralesr97.devsu.cliente_persona_service.events.ClientEvent;
import com.damoralesr97.devsu.cliente_persona_service.service.interfaces.IClientService;
import com.damoralesr97.devsu.cliente_persona_service.utils.enums.EventType;
import com.damoralesr97.devsu.cliente_persona_service.utils.exceptions.AlreadyExistsException;
import com.damoralesr97.devsu.cliente_persona_service.utils.exceptions.NotFoundExcepcion;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import com.damoralesr97.devsu.cliente_persona_service.repository.IClientRepository;
import com.damoralesr97.devsu.cliente_persona_service.mapper.ClientMapper;
import com.damoralesr97.devsu.cliente_persona_service.model.Client;
import com.damoralesr97.devsu.cliente_persona_service.dto.client.request.ClientRequest; 
import com.damoralesr97.devsu.cliente_persona_service.dto.client.response.ClientResponse;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements IClientService {

    private final Environment env;
    private final IClientRepository clientRepository;
    private final ClientEventService clientEventService;
    private final ClientMapper clientMapper;

    @Override
    public List<ClientResponse> findAll() {
        return clientRepository.findAll().stream().map(clientMapper::toResponse).toList();
    }

    @Override
    public Optional<ClientResponse> findByDni(String dni) {
        return clientRepository.findByDni(dni).map(clientMapper::toResponse);
    }

    @Override
    public ClientResponse save(ClientAccountRequest request) {
        Optional<ClientResponse> existClient = findByDni(request.getDni());
        if (existClient.isPresent()) {
            throw new AlreadyExistsException("Client with Dni " + existClient.get().getDni() + " already exists.");
        }
        Client client = clientMapper.clientAccountToEntity(request);
        client.setStatus(Boolean.TRUE);
        client = clientRepository.save(client);

        EventAccountRequest accountRequest = EventAccountRequest.builder()
                .accountNumber(request.getAccountNumber())
                .typeAccount(request.getTypeAccount())
                .initialBalance(request.getInitialBalance())
                .clientId(client.getId())
                .build();
        clientEventService.publish(env.getProperty("client-topic"), generateClientEvent(EventType.CREATED, accountRequest));

        return clientMapper.toResponse(client);
    }

    @Override
    public ClientResponse update(String dni, ClientRequest request) {
        Client existingEntity = clientRepository.findByDni(dni)
                .orElseThrow(() -> new NotFoundExcepcion("Client not found with dni " + dni));
        
        clientMapper.updateFromDto(request, existingEntity);
        
        Client savedEntity = clientRepository.save(existingEntity);
        return clientMapper.toResponse(savedEntity);
    }

    @Override
    public void delete(String dni) {
        Client client = clientRepository.findByDni(dni)
                .orElseThrow(() -> new NotFoundExcepcion("Client not found with dni " + dni));
        client.setStatus(Boolean.FALSE);
        clientRepository.save(client);

        EventAccountRequest accountRequest = EventAccountRequest.builder()
                .clientId(client.getId())
                .build();
        clientEventService.publish(env.getProperty("client-topic"), generateClientEvent(EventType.DELETED, accountRequest));
    }

    private ClientEvent generateClientEvent(EventType eventType, EventAccountRequest request) {
        ClientEvent event = new ClientEvent();
        event.setId(UUID.randomUUID().toString());
        event.setDate(new Date());
        event.setData(request);
        event.setType(eventType);
        return event;
    }

}
package com.damoralesr97.devsu.cliente_persona_service.mapper;

import com.damoralesr97.devsu.cliente_persona_service.dto.client.request.ClientAccountRequest;
import org.mapstruct.Mapper;
import com.damoralesr97.devsu.cliente_persona_service.dto.client.request.ClientRequest;
import com.damoralesr97.devsu.cliente_persona_service.dto.client.response.ClientResponse;
import com.damoralesr97.devsu.cliente_persona_service.model.Client;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    ClientResponse toResponse(Client entity);

    @Mapping(target = "status", ignore = true)
    Client toEntity(ClientRequest dto);

    @Mapping(target = "status", ignore = true)
    Client clientAccountToEntity(ClientAccountRequest dto);

    void updateFromDto(ClientRequest dto, @MappingTarget Client entity);

}
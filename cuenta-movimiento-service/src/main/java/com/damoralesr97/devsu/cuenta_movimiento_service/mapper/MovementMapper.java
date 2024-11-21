package com.damoralesr97.devsu.cuenta_movimiento_service.mapper;

import org.mapstruct.Mapper;
import com.damoralesr97.devsu.cuenta_movimiento_service.dto.movement.request.MovementRequest;
import com.damoralesr97.devsu.cuenta_movimiento_service.dto.movement.response.MovementResponse;
import com.damoralesr97.devsu.cuenta_movimiento_service.model.Movement;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface MovementMapper {

    MovementResponse toResponse(Movement entity);

    @Mapping(target = "movementDate", ignore = true)
    @Mapping(target = "movementType", ignore = true)
    @Mapping(target = "balance", ignore = true)
    @Mapping(target = "account", ignore = true)
    Movement toEntity(MovementRequest dto);

    void updateFromDto(MovementRequest dto, @MappingTarget Movement entity);

}
package com.damoralesr97.devsu.cuenta_movimiento_service.mapper;

import com.damoralesr97.devsu.cuenta_movimiento_service.dto.report.response.ReportResponse;
import com.damoralesr97.devsu.cuenta_movimiento_service.model.Movement;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReportMapper {

    @Mapping(source = "account.accountNumber", target = "accountNumber")
    @Mapping(source = "account.typeAccount", target = "accountType")
    @Mapping(source = "account.initialBalance", target = "initialBalance")
    @Mapping(source = "account.status", target = "status")
    @Mapping(source = "value", target = "movement")
    @Mapping(source = "balance", target = "availableBalance")
    @Mapping(ignore = true, target = "client")
    ReportResponse toResponse(Movement entity);

}
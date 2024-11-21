package com.damoralesr97.devsu.cuenta_movimiento_service.mapper;

import com.damoralesr97.devsu.cuenta_movimiento_service.dto.account.event.EventAccountRequest;
import org.mapstruct.Mapper;
import com.damoralesr97.devsu.cuenta_movimiento_service.dto.account.request.AccountRequest;
import com.damoralesr97.devsu.cuenta_movimiento_service.dto.account.response.AccountResponse;
import com.damoralesr97.devsu.cuenta_movimiento_service.model.Account;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    AccountResponse toResponse(Account entity);

    @Mapping(target = "movements", ignore = true)
    @Mapping(target = "status", ignore = true)
    Account toEntity(AccountRequest dto);

    @Mapping(target = "movements", ignore = true)
    @Mapping(target = "status", ignore = true)
    Account eventRequestToEntity(EventAccountRequest dto);

    void updateFromDto(AccountRequest dto, @MappingTarget Account entity);

    Account toEntityFromResponse(AccountResponse response);
}
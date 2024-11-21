package com.damoralesr97.devsu.cuenta_movimiento_service.event;

import com.damoralesr97.devsu.cuenta_movimiento_service.dto.account.event.EventAccountRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ClientEvent extends Event<EventAccountRequest>{
}

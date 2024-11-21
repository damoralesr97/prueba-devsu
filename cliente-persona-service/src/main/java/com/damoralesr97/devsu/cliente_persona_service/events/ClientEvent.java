package com.damoralesr97.devsu.cliente_persona_service.events;

import com.damoralesr97.devsu.cliente_persona_service.dto.account.event.EventAccountRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ClientEvent extends Event<EventAccountRequest> {

}

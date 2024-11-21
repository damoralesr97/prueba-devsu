package com.damoralesr97.devsu.cuenta_movimiento_service.service;

import com.damoralesr97.devsu.cuenta_movimiento_service.dto.account.response.AccountResponse;
import com.damoralesr97.devsu.cuenta_movimiento_service.event.ClientEvent;
import com.damoralesr97.devsu.cuenta_movimiento_service.event.Event;
import com.damoralesr97.devsu.cuenta_movimiento_service.service.interfaces.IAccountService;
import com.damoralesr97.devsu.cuenta_movimiento_service.utils.enums.EventType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.kafka.support.KafkaHeaders.TOPIC;

@Service
@RequiredArgsConstructor
public class ClientEventService {

    private final IAccountService accountService;

    @KafkaListener(
            topics = "CLIENTS",
            containerFactory = "kafkaListenerContainerFactory",
            groupId = "grupo1"
    )
    public void consumer(Event<?> event) {
        if (event.getClass().isAssignableFrom(ClientEvent.class)) {
            ClientEvent clientEvent = (ClientEvent) event;
            if (EventType.CREATED.equals(clientEvent.getType())) {
                accountService.save(clientEvent.getData());
            } else if (EventType.DELETED.equals(clientEvent.getType())) {
                List<AccountResponse> accounts = accountService.findByClientId(clientEvent.getData().getClientId());
                for (AccountResponse account : accounts) {
                    accountService.delete(account.getAccountNumber());
                }
            }
        }
    }

}

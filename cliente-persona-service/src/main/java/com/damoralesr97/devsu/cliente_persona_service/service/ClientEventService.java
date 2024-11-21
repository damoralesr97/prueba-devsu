package com.damoralesr97.devsu.cliente_persona_service.service;

import com.damoralesr97.devsu.cliente_persona_service.events.ClientEvent;
import com.damoralesr97.devsu.cliente_persona_service.events.Event;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientEventService {

    private final KafkaTemplate<String, Event<?>> producer;

    public void publish(String topic, ClientEvent createdEvent) {
        producer.send(topic, createdEvent);
    }

}

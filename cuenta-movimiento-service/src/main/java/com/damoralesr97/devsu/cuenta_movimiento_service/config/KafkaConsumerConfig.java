package com.damoralesr97.devsu.cuenta_movimiento_service.config;

import com.damoralesr97.devsu.cuenta_movimiento_service.event.Event;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
@RequiredArgsConstructor
public class KafkaConsumerConfig {

    private final Environment env;

    @Bean
    public ConsumerFactory<String, Event<?>> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                env.getProperty("kafka.bootstrap-servers"));
        props.put(
                ConsumerConfig.GROUP_ID_CONFIG,
                "grupo1");
        props.put(
                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class);
        props.put(
                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                JsonDeserializer.class);

        JsonDeserializer<Event<?>> jsonDeserializer = new JsonDeserializer<>(Event.class, false);
        jsonDeserializer.addTrustedPackages("com.damoralesr97.devsu.cliente_persona_service.events", "com.damoralesr97.devsu.cuenta_movimiento_service.events");
        jsonDeserializer.setUseTypeHeaders(false);

        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), jsonDeserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Event<?>> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Event<?>> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}
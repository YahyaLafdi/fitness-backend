package com.metamafitness.fitnessbackend.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.metamafitness.fitnessbackend.model.Notification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaConsumer {

    private final String notificationTopicName = "${kafka.topic.name}";

    private final ObjectMapper objectMapper;

    public KafkaConsumer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = notificationTopicName)
    public void consumeMessage(String message) throws JsonProcessingException {
        log.warn("notification consumed {}", message);

        Notification foodOrderDto = objectMapper.readValue(message, Notification.class);
        log.warn(foodOrderDto.getTitle());
    }
}

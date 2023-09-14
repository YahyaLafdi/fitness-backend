package com.metamafitness.fitnessbackend.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.metamafitness.fitnessbackend.model.Notification;
import com.metamafitness.fitnessbackend.service.KafkaProducerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaProducerServiceImpl implements KafkaProducerService {

    @Value("${kafka.topic.name}")
    private String notificationTopic;

    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaProducerServiceImpl(ObjectMapper objectMapper, KafkaTemplate<String, String> kafkaTemplate) {
        this.objectMapper = objectMapper;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public String sendMessage(Notification notification) throws JsonProcessingException {
        String notificationAsMessage = objectMapper.writeValueAsString(notification);
        kafkaTemplate.send(notificationTopic, notificationAsMessage);

        log.info("notification produced {}", notificationAsMessage);

        return "message sent";
    }
}

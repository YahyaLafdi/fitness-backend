package com.metamafitness.fitnessbackend.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.metamafitness.fitnessbackend.model.Notification;
import com.metamafitness.fitnessbackend.service.KafkaProducerService;
import com.metamafitness.fitnessbackend.service.NotificationService;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final KafkaProducerService kafkaProducerService;

    public NotificationServiceImpl(KafkaProducerService kafkaProducerService) {
        this.kafkaProducerService = kafkaProducerService;
    }

    @Override
    public String pushNotification(Notification notification) throws JsonProcessingException {
        return kafkaProducerService.sendMessage(notification);
    }
}



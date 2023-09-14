package com.metamafitness.fitnessbackend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.metamafitness.fitnessbackend.model.Notification;

public interface NotificationService {
    String pushNotification(Notification notification) throws JsonProcessingException;
}

package com.metamafitness.fitnessbackend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.metamafitness.fitnessbackend.model.Notification;
import com.metamafitness.fitnessbackend.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/demo")
@Slf4j
public class TestController {

    private final NotificationService notificationService;


    public TestController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping
    public String pushNotification(@RequestBody Notification notification) throws JsonProcessingException {
        log.info("push notification");
        return notificationService.pushNotification(notification);
    }
}

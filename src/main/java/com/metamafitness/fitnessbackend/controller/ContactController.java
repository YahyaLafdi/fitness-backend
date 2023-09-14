package com.metamafitness.fitnessbackend.controller;

import com.metamafitness.fitnessbackend.dto.ContactUsDto;
import com.metamafitness.fitnessbackend.dto.GenericDto;
import com.metamafitness.fitnessbackend.model.GenericEntity;
import com.metamafitness.fitnessbackend.model.User;
import com.metamafitness.fitnessbackend.service.MailSenderService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/contact-us")
public class ContactController extends GenericController<GenericEntity, GenericDto> {

    private final MailSenderService emailService;

    @Value("${spring.mail.username}")
    private String toEmail;

    public ContactController(MailSenderService emailService) {
        this.emailService = emailService;
    }

    @PostMapping
    public ResponseEntity<?> contactUs(@Valid @RequestBody ContactUsDto dto) {
        final User user = getCurrentUser();
        final String subject = dto.getSubject();
        Map<String, Object> params = new HashMap<>();
        params.put("email", dto.getEmail());
        params.put("phone", dto.getPhone());
        params.put("name", dto.getName());
        params.put("user", user);
        params.put("body", dto.getBody());
        params.put("subject", subject);
        params.put("signature", "sig");

        emailService.sendEmail(toEmail, subject, params, "contact-us.html");

        return new ResponseEntity<>(HttpStatus.OK);
    }

}

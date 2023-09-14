package com.metamafitness.fitnessbackend.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContactUsDto {

    private String name;

    private String email;

    private String phone;
    @NotBlank
    private String subject;
    @NotBlank
    private String body;
}
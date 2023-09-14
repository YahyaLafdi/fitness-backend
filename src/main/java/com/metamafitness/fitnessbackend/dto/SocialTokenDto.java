package com.metamafitness.fitnessbackend.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SocialTokenDto {
    @NotBlank
    String value;
}

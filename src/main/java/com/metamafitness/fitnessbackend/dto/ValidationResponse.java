package com.metamafitness.fitnessbackend.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ValidationResponse {

    private String field;
    private String message;
}

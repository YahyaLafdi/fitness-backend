package com.metamafitness.fitnessbackend.exception.handler;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorResponse {

    private int code;
    private HttpStatus status;
    private String message;

}
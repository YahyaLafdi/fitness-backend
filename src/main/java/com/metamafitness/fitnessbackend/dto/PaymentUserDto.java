package com.metamafitness.fitnessbackend.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentUserDto {

    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private String payerId;
}

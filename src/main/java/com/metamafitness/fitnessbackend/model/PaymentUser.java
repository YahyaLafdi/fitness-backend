package com.metamafitness.fitnessbackend.model;


import lombok.*;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentUser {
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private String payerId;
}

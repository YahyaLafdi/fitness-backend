package com.metamafitness.fitnessbackend.model;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Notification {
    private String title;
    private String details;
}

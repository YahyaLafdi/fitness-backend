package com.metamafitness.fitnessbackend.model;

import lombok.*;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class SocialMedia {
    private String facebook;
    private String twitter;
    private String instagram;
    private String linkedin;
}

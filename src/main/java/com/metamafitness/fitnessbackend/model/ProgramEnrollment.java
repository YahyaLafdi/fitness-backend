package com.metamafitness.fitnessbackend.model;

import lombok.*;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Entity
public class ProgramEnrollment extends GenericEntity {

    @ManyToOne
    @JoinColumn(name = "enrollee_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "program_id")
    private Program program;

    @Embedded
    private ProgramProgress progress;

    @Embedded
    private Payment payment;
}

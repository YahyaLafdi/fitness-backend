package com.metamafitness.fitnessbackend.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProgramReview extends GenericEntity {

    private int rating;

    private String review;

    @ManyToOne
    private Program program;

    @OneToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "created_by_id")
    private User createdBy;
}

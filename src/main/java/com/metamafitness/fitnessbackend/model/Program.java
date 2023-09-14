package com.metamafitness.fitnessbackend.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.metamafitness.fitnessbackend.model.GenericEnum.ProgramCategory;
import static com.metamafitness.fitnessbackend.model.GenericEnum.ProgramState;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Entity
public class Program extends GenericEntity {

    private String name;

    @Column(nullable = false)
    private String picture;

    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    private GenericEnum.ProgramLevel level;


    @Enumerated(EnumType.STRING)
    private ProgramState state;

    @Enumerated(EnumType.STRING)
    private ProgramCategory category;

    private String description;

    private String motivationDescription;

    private int durationPerDay; // in minutes

    @OneToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "created_by_id")
    private User createdBy;

    @ElementCollection(targetClass = GenericEnum.ProgramCategory.class)
    @Enumerated(EnumType.STRING)
    private Set<GenericEnum.ProgramCategory> options;

    @ElementCollection(targetClass = GenericEnum.ProgramEquipment.class)
    @Enumerated(EnumType.STRING)
    private Set<GenericEnum.ProgramEquipment> equipments;


    @OneToMany(mappedBy = "program", cascade = CascadeType.ALL)
    private List<ProgramSection> sections;

    @OneToMany(mappedBy = "program", cascade = CascadeType.ALL)
    private List<ProgramEnrollment> enrollments;


    @OneToMany(mappedBy = "program", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<ProgramReview> reviews = new ArrayList<>();
}

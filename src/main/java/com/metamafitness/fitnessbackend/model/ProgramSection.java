package com.metamafitness.fitnessbackend.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class ProgramSection extends GenericEntity {

    private String title;

    private String description;

    @Enumerated(EnumType.STRING)
    private GenericEnum.SectionLevel level;


    @ManyToOne
    @JoinColumn(name = "program_id")
    private Program program;

    @Embedded
    private SectionVideo video;

    @OneToMany(mappedBy = "section", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SectionComment> comments = new ArrayList<>();
}

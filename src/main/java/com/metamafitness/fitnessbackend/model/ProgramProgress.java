package com.metamafitness.fitnessbackend.model;


import lombok.*;

import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProgramProgress {

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private int progress;
}

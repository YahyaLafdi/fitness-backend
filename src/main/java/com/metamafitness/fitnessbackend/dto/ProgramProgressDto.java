package com.metamafitness.fitnessbackend.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProgramProgressDto {

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private int progress;
}

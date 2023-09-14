package com.metamafitness.fitnessbackend.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class GenericDto {

    private Long id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


}

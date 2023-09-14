package com.metamafitness.fitnessbackend.dto;

import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewPatchDto {
    @Max(5) @Min(1)
    private int rating;

    private String review;
}

package com.metamafitness.fitnessbackend.dto;

import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProgramReviewDto extends GenericDto{

    @Max(5) @Min(1)
    private int rating;

    private String review;

    private ProgramDto program;

    private UserDto createdBy;


}

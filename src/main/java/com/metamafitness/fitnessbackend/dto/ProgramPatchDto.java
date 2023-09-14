package com.metamafitness.fitnessbackend.dto;

import com.metamafitness.fitnessbackend.model.GenericEnum;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProgramPatchDto {


    @Size(max = 255)
    private String name;


    private GenericEnum.ProgramLevel level;

    @DecimalMin(value = "0.00")
    private BigDecimal price;


    private GenericEnum.ProgramCategory category;


    private String description;


    @Size(max = 255)
    private String motivationDescription;


    @Positive
    private int durationPerDay; // in minutes

    @NotEmpty
    private Set<GenericEnum.ProgramCategory> options;

    @NotEmpty
    private Set<GenericEnum.ProgramEquipment> equipments;

}

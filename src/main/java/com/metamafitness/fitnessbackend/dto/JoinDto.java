package com.metamafitness.fitnessbackend.dto;

import com.metamafitness.fitnessbackend.model.GenericEnum;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JoinDto extends GenericDto{
    @NotNull
    private GenericEnum.ProgramCategory expertise;//specialisation
    @NotNull
    private String message;
    private Boolean approved;
    private UserDto sender;
    private String experience;
    private List<String> documents;
}

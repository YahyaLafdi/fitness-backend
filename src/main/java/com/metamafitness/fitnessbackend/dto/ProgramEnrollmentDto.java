package com.metamafitness.fitnessbackend.dto;


import com.metamafitness.fitnessbackend.model.Program;
import com.metamafitness.fitnessbackend.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProgramEnrollmentDto extends GenericDto {
    private UserDto user;

    private ProgramDto program;

    private ProgramProgressDto progress;

    private PaymentDto payment;
}

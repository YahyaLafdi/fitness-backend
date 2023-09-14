package com.metamafitness.fitnessbackend.dto;

import com.metamafitness.fitnessbackend.validator.ValidPassword;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResetPasswordRequest {
    @NotBlank
    @ValidPassword
    private String newPassword;
}

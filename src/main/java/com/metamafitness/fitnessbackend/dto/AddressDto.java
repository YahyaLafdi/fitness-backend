package com.metamafitness.fitnessbackend.dto;

import com.metamafitness.fitnessbackend.common.CoreConstant;
import lombok.*;

import javax.validation.constraints.Pattern;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressDto {

    private String country;
    private String city;

    @Pattern(regexp = CoreConstant.Validation.POSTAL_CODE_REGEX)
    private String postalCode;


}

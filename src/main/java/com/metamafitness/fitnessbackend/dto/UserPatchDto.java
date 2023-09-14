package com.metamafitness.fitnessbackend.dto;

import com.metamafitness.fitnessbackend.model.GenericEnum;
import com.metamafitness.fitnessbackend.validator.ValidPhoneNumber;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserPatchDto {

    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    private LocalDate birthDay;
    private String bio;
    private GenericEnum.Gender gender;
    private AddressDto address;
    private PhoneNumberDto phoneNumber;

    private SocialMediaDto socialMedia;
}

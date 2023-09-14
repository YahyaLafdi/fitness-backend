package com.metamafitness.fitnessbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

import static com.metamafitness.fitnessbackend.model.GenericEnum.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BlogDto extends GenericDto{
    @NotBlank
    @Size(max = 255)
    private String name;
    private String picture;
    @NotBlank
    private String content;
    private BlogState state;
    private UserDto createdBy;
    private Set<String> tags;
}

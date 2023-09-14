package com.metamafitness.fitnessbackend.validator;

import com.metamafitness.fitnessbackend.validator.validation.VideoFileValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = VideoFileValidator.class)
public @interface ValidVideoFiles {
    String message() default "Only video files are allowed";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

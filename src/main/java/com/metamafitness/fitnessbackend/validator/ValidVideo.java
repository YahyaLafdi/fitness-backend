package com.metamafitness.fitnessbackend.validator;

import com.metamafitness.fitnessbackend.validator.validation.VideoValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = VideoValidator.class)
public @interface ValidVideo {

    String message() default "Only image files are allowed";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

package com.metamafitness.fitnessbackend.validator;

import com.metamafitness.fitnessbackend.validator.validation.PictureValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PictureValidator.class)
public @interface ValidPicture {
    String message() default "Only image files are allowed";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

package com.metamafitness.fitnessbackend.validator;

import com.metamafitness.fitnessbackend.validator.validation.PreviewPicturesValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PreviewPicturesValidator.class)
public @interface ValidPreviewPictures {
    String message() default "Only image files are allowed";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

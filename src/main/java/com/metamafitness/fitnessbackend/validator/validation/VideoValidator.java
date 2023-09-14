package com.metamafitness.fitnessbackend.validator.validation;

import com.metamafitness.fitnessbackend.validator.ValidVideo;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class VideoValidator implements ConstraintValidator<ValidVideo, MultipartFile> {

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {

        if(Objects.isNull(file) || file.isEmpty()) return true;
        String contentType = file.getContentType();
        return Objects.requireNonNull(contentType).contains("video");
    }
}

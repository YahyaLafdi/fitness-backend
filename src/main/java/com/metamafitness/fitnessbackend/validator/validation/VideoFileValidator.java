package com.metamafitness.fitnessbackend.validator.validation;


import com.metamafitness.fitnessbackend.validator.ValidVideoFiles;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class VideoFileValidator implements ConstraintValidator<ValidVideoFiles, List<MultipartFile>> {

    @Override
    public boolean isValid(List<MultipartFile> files, ConstraintValidatorContext context) {
        for (MultipartFile file : files) {
            if (!isVideoFile(file)) {
                return false;
            }
        }
        return true;
    }

    private boolean isVideoFile(MultipartFile file) {
        String contentType = file.getContentType();
        if (contentType == null) {
            return false;
        }
        return contentType.contains("video");
    }
}
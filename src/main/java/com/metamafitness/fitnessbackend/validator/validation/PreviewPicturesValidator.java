package com.metamafitness.fitnessbackend.validator.validation;

import com.metamafitness.fitnessbackend.validator.ValidPreviewPictures;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class PreviewPicturesValidator implements ConstraintValidator<ValidPreviewPictures, List<MultipartFile>> {

    @Override
    public boolean isValid(List<MultipartFile> files, ConstraintValidatorContext context) {
        for (MultipartFile file : files) {
            if (!isImageFile(file)) {
                return false;
            }
        }
        return true;
    }

    private boolean isImageFile(MultipartFile file) {
        String contentType = file.getContentType();
        if (contentType == null) {
            return false;
        }
        return contentType.contains("image");
    }
}

package com.metamafitness.fitnessbackend.validator.validation;

import com.metamafitness.fitnessbackend.common.CoreConstant;
import com.metamafitness.fitnessbackend.dto.ProgramDto;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Component
public class ProgramFileValidator {

    private final MessageSource messageSource;

    public ProgramFileValidator(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public void validate(ProgramDto programDto, List<MultipartFile> videos, List<MultipartFile> images) {

        int numSections = programDto.getSections().size();
        int numVideos = videos.size();
        int numImages = images.size();

        boolean sectionVideoMismatch = numVideos != numSections;
        boolean videoImageMismatch = numVideos != numImages;
        boolean sectionImageMismatch = numSections != numImages;

        if (sectionVideoMismatch || videoImageMismatch || sectionImageMismatch) {
            throw new IllegalArgumentException(messageSource.getMessage(CoreConstant.Exception.VALIDATION_FILE_SECTION_MISMATCH, null, null));
        }
    }
}
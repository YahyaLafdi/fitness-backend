package com.metamafitness.fitnessbackend.controller;

import com.metamafitness.fitnessbackend.dto.ProgramSectionDto;
import com.metamafitness.fitnessbackend.dto.ProgramSectionPatchDto;
import com.metamafitness.fitnessbackend.exception.ResourceOwnershipException;
import com.metamafitness.fitnessbackend.exception.UnauthorizedException;
import com.metamafitness.fitnessbackend.model.Program;
import com.metamafitness.fitnessbackend.model.ProgramSection;
import com.metamafitness.fitnessbackend.service.ProgramSectionService;
import com.metamafitness.fitnessbackend.service.StorageService;
import com.metamafitness.fitnessbackend.validator.ValidPicture;
import com.metamafitness.fitnessbackend.validator.ValidVideo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.net.URI;
import java.util.Objects;

import static com.metamafitness.fitnessbackend.common.CoreConstant.Exception.AUTHORIZATION_RESOURCE_OWNERSHIP;


@RestController
@RequestMapping("/api/programs/sections")
@Validated
public class SectionController extends GenericController<ProgramSection, ProgramSectionDto>{

    private final ProgramSectionService programSectionService;

    private final StorageService storageService;

    public SectionController(ProgramSectionService programSectionService, StorageService storageService) {
        this.programSectionService = programSectionService;
        this.storageService = storageService;

    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProgramSectionDto> update (@PathVariable("id") Long id,
                                                  @RequestPart(value = "section", required = false) ProgramSectionPatchDto sectionPatchDto,
                                                  @Valid @ValidVideo @RequestPart(value = "video", required = false) MultipartFile video,
                                                  @Valid @ValidPicture @RequestPart(value = "preview-image", required = false) MultipartFile previewImage) {

        if(Objects.isNull(sectionPatchDto) && video.isEmpty() && previewImage.isEmpty()) {
            throw new UnauthorizedException();
        }

        ProgramSection programSectionFound = programSectionService.findById(id);
        if(isNotOwner(programSectionFound.getProgram())) {
            throw new ResourceOwnershipException(new ResourceOwnershipException(), AUTHORIZATION_RESOURCE_OWNERSHIP, null);
        }

        if(Objects.nonNull(sectionPatchDto)) {
            mapWithSkipNull(sectionPatchDto, programSectionFound);
        }

        if (!video.isEmpty()) {
            String sectionVideoURL = programSectionFound.getVideo().getVideoUrl();
            if(Objects.nonNull(sectionVideoURL)) {
                URI uri = URI.create(sectionVideoURL);
                String currentSectionVideo = uri.getPath().substring(1);
                storageService.delete(currentSectionVideo);
            }
            String newSectionVideoURL = storageService.save(video);
            programSectionFound.getVideo().setVideoUrl(newSectionVideoURL);
        }

        if (!previewImage.isEmpty()) {
            String previewImageUrl = programSectionFound.getVideo().getPreviewImageUrl();
            if(Objects.nonNull(previewImageUrl)){
                URI uri = URI.create(previewImageUrl);
                String currentSectionPreviewImage = uri.getPath().substring(1);
                storageService.delete(currentSectionPreviewImage);
            }
            String newSectionPreviewImageURL = storageService.save(previewImage);
            programSectionFound.getVideo().setPreviewImageUrl(newSectionPreviewImageURL);
        }

        ProgramSection updatedSection = programSectionService.patch(programSectionFound);

        return ResponseEntity.status(HttpStatus.OK).body(convertToDto(updatedSection));


    }

    private boolean isNotOwner(Program programFound) {
        final Long currentUserId = getCurrentUserId();
        final Long resourceOwnerId = programFound.getCreatedBy().getId();
        return !currentUserId.equals(resourceOwnerId);
    }
}

package com.metamafitness.fitnessbackend.controller;

import com.metamafitness.fitnessbackend.dto.ProgramReviewDto;
import com.metamafitness.fitnessbackend.dto.ReviewPatchDto;
import com.metamafitness.fitnessbackend.dto.SearchDto;
import com.metamafitness.fitnessbackend.exception.BusinessException;
import com.metamafitness.fitnessbackend.exception.ElementAlreadyExistException;
import com.metamafitness.fitnessbackend.exception.ResourceOwnershipException;
import com.metamafitness.fitnessbackend.model.Program;
import com.metamafitness.fitnessbackend.model.ProgramReview;
import com.metamafitness.fitnessbackend.model.User;
import com.metamafitness.fitnessbackend.service.ProgramService;
import com.metamafitness.fitnessbackend.service.ReviewService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.metamafitness.fitnessbackend.common.CoreConstant.Exception.ALREADY_EXISTS;
import static com.metamafitness.fitnessbackend.common.CoreConstant.Exception.AUTHORIZATION_RESOURCE_OWNERSHIP;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController extends GenericController<ProgramReview, ProgramReviewDto>{


    private final ProgramService programService;

    private final ReviewService reviewService;

    public ReviewController(ProgramService programService, ReviewService reviewService) {
        this.programService = programService;
        this.reviewService = reviewService;
    }

    @PostMapping("/{programId}")
    public ResponseEntity<ProgramReviewDto> addReview(@PathVariable Long programId, @RequestBody ProgramReviewDto reviewDto) {
        Program program = programService.findById(programId);
        User currentUser = getCurrentUser();
        ProgramReview programReviewFound = reviewService.findByUserAndProgramId(currentUser.getId(),program.getId());

        if(Objects.nonNull(programReviewFound)) {
            return update(programReviewFound.getId(), new ReviewPatchDto(reviewDto.getRating(), reviewDto.getReview()));
            //throw new ElementAlreadyExistException(new ElementAlreadyExistException(), ALREADY_EXISTS, null);
        }
        ProgramReview programReview = convertToEntity(reviewDto);
        programReview.setProgram(program);
        programReview.setCreatedBy(currentUser);

        ProgramReview saved = reviewService.save(programReview);

        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDto(saved));
    }

    @PatchMapping("/{reviewId}")
    public ResponseEntity<ProgramReviewDto> update(@PathVariable Long reviewId, @RequestBody ReviewPatchDto patchDto) {
        ProgramReview review = reviewService.findById(reviewId);
        if(isNotOwner(review)) {
            throw new ResourceOwnershipException(new ResourceOwnershipException(), AUTHORIZATION_RESOURCE_OWNERSHIP, null);
        }
        mapWithSkipNull(patchDto, review);
        ProgramReview updatedReview = reviewService.patch(review);

        return ResponseEntity.status(HttpStatus.OK).body(convertToDto(updatedReview));
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Boolean> delete(@PathVariable Long reviewId) {
        ProgramReview review = reviewService.findById(reviewId);
        Boolean deleted = reviewService.delete(reviewId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(deleted);
    }
    @Override
    @PostMapping("/search")
    public ResponseEntity<List<ProgramReviewDto>> search(@RequestBody SearchDto searchDto) throws BusinessException {
        searchDto.validate();
        Pageable pageable = PageRequest.of(searchDto.getPage(), searchDto.getSize());
        List<ProgramReview> reviews = reviewService.search(searchDto.getKeyword(), pageable);

        // Get the current user
        User currentUser = getCurrentUser();

        List<ProgramReviewDto> dtos = reviews.stream()
                .filter(review -> review.getProgram().getCreatedBy().equals(currentUser))
                .map(this::convertToDto)
                .collect(Collectors.toList());

        long totalElements = reviewService.countAll();
        int totalPages = (int) Math.ceil((double) totalElements / searchDto.getSize());

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Pages", String.valueOf(totalPages));
        headers.add("Access-Control-Expose-Headers", "X-Total-Pages");
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(dtos);
    }


    private boolean isNotOwner(ProgramReview review) {
        final Long currentUserId = getCurrentUserId();
        final Long resourceOwnerId = review.getCreatedBy().getId();
        return !currentUserId.equals(resourceOwnerId);
    }


}

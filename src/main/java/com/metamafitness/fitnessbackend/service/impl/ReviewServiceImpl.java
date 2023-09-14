package com.metamafitness.fitnessbackend.service.impl;

import com.metamafitness.fitnessbackend.model.ProgramReview;
import com.metamafitness.fitnessbackend.repository.GenericRepository;
import com.metamafitness.fitnessbackend.repository.ReviewRepository;
import com.metamafitness.fitnessbackend.service.ReviewService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class ReviewServiceImpl extends GenericServiceImpl<ProgramReview> implements ReviewService {

    private final ReviewRepository reviewRepository;
    public ReviewServiceImpl(GenericRepository<ProgramReview> genericRepository, ModelMapper modelMapper, ReviewRepository reviewRepository) {
        super(genericRepository, modelMapper);
        this.reviewRepository = reviewRepository;
    }

    @Override
    public Boolean findByUserAndProgram(Long userId, Long ProgramId) {
        return reviewRepository.existsByCreatedBy_idAndProgram_id(userId, ProgramId);
    }
    @Override
    public ProgramReview findByUserAndProgramId(Long userId, Long ProgramId) {
        return reviewRepository.findByCreatedBy_idAndProgram_id(userId, ProgramId);
    }

    @Override
    public Long countByTrainer(Long currentUserId) {
        return reviewRepository.countByProgram_CreatedBy_Id(currentUserId);
    }
}

package com.metamafitness.fitnessbackend.repository;

import com.metamafitness.fitnessbackend.model.ProgramReview;

public interface ReviewRepository extends GenericRepository<ProgramReview>{

    Boolean existsByCreatedBy_idAndProgram_id(Long userId, Long programId);
    ProgramReview findByCreatedBy_idAndProgram_id(Long userId, Long programId);


    Long countByProgram_CreatedBy_Id(Long currentUserId);
}

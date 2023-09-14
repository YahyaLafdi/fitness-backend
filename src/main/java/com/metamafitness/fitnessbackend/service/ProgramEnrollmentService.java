package com.metamafitness.fitnessbackend.service;


import com.metamafitness.fitnessbackend.exception.BusinessException;
import com.metamafitness.fitnessbackend.model.ProgramEnrollment;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

public interface ProgramEnrollmentService extends GenericService<ProgramEnrollment> {

    ProgramEnrollment findByUserAndProgram(Long userId, Long programId);

    List<ProgramEnrollment> findByProgramCreator(Long trainerId, Pageable pageable) throws BusinessException;

    long countByProgramCreator(Long currentUserId);

    BigDecimal getTotalProfitByTrainer(Long trainerId);

    BigDecimal getTotalProfit();
}

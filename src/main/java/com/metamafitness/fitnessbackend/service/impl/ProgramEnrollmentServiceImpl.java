package com.metamafitness.fitnessbackend.service.impl;


import com.metamafitness.fitnessbackend.common.CoreConstant;
import com.metamafitness.fitnessbackend.exception.BusinessException;
import com.metamafitness.fitnessbackend.model.ProgramEnrollment;
import com.metamafitness.fitnessbackend.repository.GenericRepository;
import com.metamafitness.fitnessbackend.repository.ProgramEnrollmentRepository;
import com.metamafitness.fitnessbackend.service.ProgramEnrollmentService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ProgramEnrollmentServiceImpl extends GenericServiceImpl<ProgramEnrollment> implements ProgramEnrollmentService {
    private final ProgramEnrollmentRepository programEnrollmentRepository;

    public ProgramEnrollmentServiceImpl(GenericRepository<ProgramEnrollment> genericRepository, ModelMapper modelMapper, ProgramEnrollmentRepository programEnrollmentRepository) {
        super(genericRepository, modelMapper);
        this.programEnrollmentRepository = programEnrollmentRepository;
    }

    @Override
    public ProgramEnrollment findByUserAndProgram(Long userId, Long programId) {
        Optional<ProgramEnrollment> enrollmentFound = programEnrollmentRepository.findByUser_idAndProgram_id(userId, programId);
        return enrollmentFound.orElse(null);
    }

    @Override
    public List<ProgramEnrollment> findByProgramCreator(Long trainerId, Pageable pageable) throws BusinessException {
        try {
            return programEnrollmentRepository.findByProgram_CreatedBy_id(trainerId, pageable);
        } catch (BusinessException e) {
            throw new BusinessException(null, e, CoreConstant.Exception.FIND_ELEMENTS, null);

        }
    }

    @Override
    public long countByProgramCreator(Long trainerId) {
        return programEnrollmentRepository.countByProgram_CreatedBy_id(trainerId);
    }

    @Override
    public BigDecimal getTotalProfitByTrainer(Long trainerId) {
        return programEnrollmentRepository.sumPayment_PaymentAmountByProgramCreatedBy_Id(trainerId);
    }

    @Override
    public BigDecimal getTotalProfit() {
        return programEnrollmentRepository.sumPayment_PaymentAmount();
    }
}

package com.metamafitness.fitnessbackend.repository;

import com.metamafitness.fitnessbackend.model.ProgramEnrollment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ProgramEnrollmentRepository extends GenericRepository<ProgramEnrollment>{

    Optional<ProgramEnrollment> findByUser_idAndProgram_id(Long userId, Long programId);
    boolean existsByPayment_transactionId(String transactionId);

    List<ProgramEnrollment> findByProgram_CreatedBy_id(Long id, Pageable pageable);

    Long countByProgram_CreatedBy_id(Long id);

    @Query("SELECT COALESCE(SUM(pe.payment.paymentAmount), 0) FROM ProgramEnrollment pe WHERE pe.program.createdBy.id = :id")
    BigDecimal sumPayment_PaymentAmountByProgramCreatedBy_Id(Long id);
    @Query("SELECT SUM(pe.payment.paymentAmount) FROM ProgramEnrollment pe")
    BigDecimal sumPayment_PaymentAmount();
}

package com.metamafitness.fitnessbackend.service;

import com.metamafitness.fitnessbackend.exception.BusinessException;
import com.metamafitness.fitnessbackend.model.GenericEnum;
import com.metamafitness.fitnessbackend.model.Program;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProgramService extends GenericService<Program> {

    List<Program> findByEnrollment(Long userId, int page, int size);

    long countByEnrollment(Long userId);

    List<Program> searchWithCategory(String keyword, Pageable pageable,
                         String state,
                         String category) throws BusinessException;

    List<Program> findByCreator(Long id, int page, int size);

    long countByCreator(Long currentUserId);
    List<Program> findByCategory(GenericEnum.ProgramCategory category, int page, int size);
    long countByCategory(GenericEnum.ProgramCategory category);
}

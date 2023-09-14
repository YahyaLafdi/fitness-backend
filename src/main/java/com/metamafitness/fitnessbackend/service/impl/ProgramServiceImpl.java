package com.metamafitness.fitnessbackend.service.impl;

import com.metamafitness.fitnessbackend.common.CoreConstant;
import com.metamafitness.fitnessbackend.exception.BusinessException;
import com.metamafitness.fitnessbackend.exception.ElementNotFoundException;
import com.metamafitness.fitnessbackend.model.GenericEnum;
import com.metamafitness.fitnessbackend.model.Program;
import com.metamafitness.fitnessbackend.repository.GenericRepository;
import com.metamafitness.fitnessbackend.repository.ProgramRepository;
import com.metamafitness.fitnessbackend.service.ProgramService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProgramServiceImpl extends GenericServiceImpl<Program> implements ProgramService {

    private final ProgramRepository programRepository;

    public ProgramServiceImpl(GenericRepository<Program> genericRepository, ModelMapper modelMapper, ProgramRepository programRepository) {
        super(genericRepository, modelMapper);
        this.programRepository = programRepository;

    }

    @Override
    public List<Program> findByEnrollment(Long userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return programRepository.findByEnrollments_user_id(userId, pageable);
    }

    @Override
    public long countByEnrollment(Long userId) {
        return programRepository.countByEnrollments_user_id(userId);
    }

    @Override
    public List<Program> searchWithCategory(String keyword, Pageable pageable, String state, String category) throws BusinessException {
        try {
            return programRepository.findByCategoryAndState(category,state, keyword, pageable);
        } catch (BusinessException e) {
            throw new BusinessException(null, e, CoreConstant.Exception.FIND_ELEMENTS, null);

        }
    }

    @Override
    public List<Program> findByCreator(Long id, int page, int size) throws ElementNotFoundException {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "state").and(Sort.by(Sort.Direction.DESC, "createdAt")));
        return programRepository.findByCreatedBy_id(id, pageable);
    }

    @Override
    public long countByCreator(Long currentUserId) {
        return programRepository.countByCreatedBy_id(currentUserId);
    }
    @Override
    public List<Program> findByCategory(GenericEnum.ProgramCategory category, int page, int size) throws ElementNotFoundException {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "state").and(Sort.by(Sort.Direction.DESC, "createdAt")));
        return programRepository.findByCategory(category, pageable);
    }
    @Override
    public long countByCategory(GenericEnum.ProgramCategory category) {
        return programRepository.countByCategory(category);
    }
}

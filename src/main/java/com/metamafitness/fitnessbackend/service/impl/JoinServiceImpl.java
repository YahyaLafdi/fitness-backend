package com.metamafitness.fitnessbackend.service.impl;

import com.metamafitness.fitnessbackend.common.CoreConstant;
import com.metamafitness.fitnessbackend.exception.ElementAlreadyExistException;
import com.metamafitness.fitnessbackend.model.Join;
import com.metamafitness.fitnessbackend.repository.GenericRepository;
import com.metamafitness.fitnessbackend.repository.JoinRepository;
import com.metamafitness.fitnessbackend.service.JoinService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JoinServiceImpl extends GenericServiceImpl<Join> implements JoinService {

    private final JoinRepository joinRepository;

    public JoinServiceImpl(GenericRepository<Join> genericRepository, ModelMapper modelMapper, JoinRepository joinRepository) {
        super(genericRepository, modelMapper);
        this.joinRepository = joinRepository;
    }

    @Override
    public Join save(Join entity) throws ElementAlreadyExistException {
        final Long senderId = entity.getSender().getId();
        final Optional<Join> entityExist = joinRepository.findBySender_id(senderId);

        if (entityExist.isEmpty()) {
            return joinRepository.save(entity);
        } else {
            LOG.warn(CoreConstant.Exception.ALREADY_EXISTS);
            throw new ElementAlreadyExistException(null, new ElementAlreadyExistException(), CoreConstant.Exception.ALREADY_EXISTS, new Object[]{});
        }
    }


    @Override
    public Page<Join> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("approved").ascending().and(Sort.by("createdAt").descending()).and(Sort.by("updatedAt").ascending()));
        return joinRepository.findAll(pageable);
    }
}

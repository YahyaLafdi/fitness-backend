package com.metamafitness.fitnessbackend.service.impl;

import com.metamafitness.fitnessbackend.model.SectionComment;
import com.metamafitness.fitnessbackend.repository.GenericRepository;
import com.metamafitness.fitnessbackend.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl extends GenericServiceImpl<SectionComment> implements CommentService {
    public CommentServiceImpl(GenericRepository<SectionComment> genericRepository, ModelMapper modelMapper) {
        super(genericRepository, modelMapper);
    }
}

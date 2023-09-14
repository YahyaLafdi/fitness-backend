package com.metamafitness.fitnessbackend.service.impl;

import com.metamafitness.fitnessbackend.model.ProgramSection;
import com.metamafitness.fitnessbackend.repository.GenericRepository;
import com.metamafitness.fitnessbackend.service.ProgramSectionService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class ProgramSectionServiceImpl extends GenericServiceImpl<ProgramSection> implements ProgramSectionService {

    public ProgramSectionServiceImpl(GenericRepository<ProgramSection> genericRepository, ModelMapper modelMapper) {
        super(genericRepository, modelMapper);
    }
}

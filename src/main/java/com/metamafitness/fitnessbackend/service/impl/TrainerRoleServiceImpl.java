package com.metamafitness.fitnessbackend.service.impl;

import com.metamafitness.fitnessbackend.common.CoreConstant;
import com.metamafitness.fitnessbackend.exception.ElementAlreadyExistException;
import com.metamafitness.fitnessbackend.exception.ElementNotFoundException;
import com.metamafitness.fitnessbackend.model.GenericEnum;
import com.metamafitness.fitnessbackend.model.TrainerRole;
import com.metamafitness.fitnessbackend.repository.GenericRepository;
import com.metamafitness.fitnessbackend.repository.TrainerRoleRepository;
import com.metamafitness.fitnessbackend.service.TrainerRoleService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TrainerRoleServiceImpl extends GenericServiceImpl<TrainerRole> implements TrainerRoleService {
    private final TrainerRoleRepository trainerRoleRepository;

    @Override
    public TrainerRole findByName(GenericEnum.RoleName roleName) {
        Optional<TrainerRole> userRole = trainerRoleRepository.findByName(roleName);
        if (userRole.isPresent()) return userRole.get();
        throw new ElementNotFoundException(null, new ElementNotFoundException(), CoreConstant.Exception.NOT_FOUND, new Object[]{roleName});
    }

    public TrainerRoleServiceImpl(GenericRepository<TrainerRole> genericRepository, ModelMapper modelMapper, TrainerRoleRepository trainerRoleRepository) {
        super(genericRepository, modelMapper);
        this.trainerRoleRepository = trainerRoleRepository;

    }

    @Override
    public TrainerRole save(TrainerRole entity) throws ElementAlreadyExistException {
        Optional<TrainerRole> trainerRole = trainerRoleRepository.findByName(GenericEnum.RoleName.TRAINER);
        return trainerRole.orElseGet(() -> trainerRoleRepository.save(new TrainerRole()));
    }
}

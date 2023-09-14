package com.metamafitness.fitnessbackend.service.impl;

import com.metamafitness.fitnessbackend.common.CoreConstant;
import com.metamafitness.fitnessbackend.exception.ElementAlreadyExistException;
import com.metamafitness.fitnessbackend.exception.ElementNotFoundException;

import com.metamafitness.fitnessbackend.model.DevRole;
import com.metamafitness.fitnessbackend.model.GenericEnum;

import com.metamafitness.fitnessbackend.repository.DevRoleRepository;
import com.metamafitness.fitnessbackend.repository.GenericRepository;
import com.metamafitness.fitnessbackend.service.DevRoleService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DevRoleServiceImpl extends GenericServiceImpl<DevRole> implements DevRoleService {
    private final DevRoleRepository devRoleRepository;

    public DevRoleServiceImpl(GenericRepository<DevRole> genericRepository, ModelMapper modelMapper, DevRoleRepository devRoleRepository) {
        super(genericRepository, modelMapper);
        this.devRoleRepository = devRoleRepository;
    }


    @Override
    public DevRole findByName(GenericEnum.RoleName roleName) {
        Optional<DevRole> devRole = devRoleRepository.findByName(roleName);
        if (devRole.isPresent()) return devRole.get();
        throw new ElementNotFoundException(null, new ElementNotFoundException(), CoreConstant.Exception.NOT_FOUND, new Object[]{roleName});

    }

    public DevRole save(DevRole entity) throws ElementAlreadyExistException {
        Optional<DevRole> devRole = devRoleRepository.findByName(GenericEnum.RoleName.DEV);
        return devRole.orElseGet(() -> devRoleRepository.save(new DevRole()));
    }
}

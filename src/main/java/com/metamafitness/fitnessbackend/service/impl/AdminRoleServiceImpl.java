package com.metamafitness.fitnessbackend.service.impl;

import com.metamafitness.fitnessbackend.common.CoreConstant;
import com.metamafitness.fitnessbackend.exception.ElementAlreadyExistException;
import com.metamafitness.fitnessbackend.exception.ElementNotFoundException;
import com.metamafitness.fitnessbackend.model.AdminRole;
import com.metamafitness.fitnessbackend.model.GenericEnum;
import com.metamafitness.fitnessbackend.repository.AdminRoleRepository;
import com.metamafitness.fitnessbackend.repository.GenericRepository;
import com.metamafitness.fitnessbackend.service.AdminRoleService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminRoleServiceImpl extends GenericServiceImpl<AdminRole> implements AdminRoleService {
    private final AdminRoleRepository adminRoleRepository;

    public AdminRoleServiceImpl(GenericRepository<AdminRole> genericRepository, ModelMapper modelMapper, AdminRoleRepository adminRoleRepository) {
        super(genericRepository, modelMapper);
        this.adminRoleRepository = adminRoleRepository;
    }


    @Override
    public AdminRole findByName(GenericEnum.RoleName roleName) {
        Optional<AdminRole> userRole = adminRoleRepository.findByName(roleName);
        if (userRole.isPresent()) return userRole.get();
        throw new ElementNotFoundException(null, new ElementNotFoundException(), CoreConstant.Exception.NOT_FOUND, new Object[]{roleName});

    }

    public AdminRole save(AdminRole entity) throws ElementAlreadyExistException {
        Optional<AdminRole> userRole = adminRoleRepository.findByName(GenericEnum.RoleName.ADMIN);
        return userRole.orElseGet(() -> adminRoleRepository.save(new AdminRole()));
    }
}

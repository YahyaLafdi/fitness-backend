package com.metamafitness.fitnessbackend.service.impl;

import com.metamafitness.fitnessbackend.common.CoreConstant;
import com.metamafitness.fitnessbackend.exception.ElementAlreadyExistException;
import com.metamafitness.fitnessbackend.exception.ElementNotFoundException;
import com.metamafitness.fitnessbackend.model.GenericEnum;
import com.metamafitness.fitnessbackend.model.UserRole;
import com.metamafitness.fitnessbackend.repository.GenericRepository;
import com.metamafitness.fitnessbackend.repository.UserRoleRepository;
import com.metamafitness.fitnessbackend.service.UserRoleService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserRoleServiceImpl extends GenericServiceImpl<UserRole> implements UserRoleService {
    private final UserRoleRepository userRoleRepository;



    @Override
    public UserRole findByName(GenericEnum.RoleName roleName) {
        Optional<UserRole> userRole = userRoleRepository.findByName(roleName);
        if (userRole.isPresent()) return userRole.get();
        throw new ElementNotFoundException(null, new ElementNotFoundException(), CoreConstant.Exception.NOT_FOUND, new Object[]{roleName});
    }


    public UserRoleServiceImpl(GenericRepository<UserRole> genericRepository, ModelMapper modelMapper, UserRoleRepository userRoleRepository) {
        super(genericRepository, modelMapper);
        this.userRoleRepository = userRoleRepository;
    }

    public UserRole save(UserRole entity) throws ElementAlreadyExistException {
        Optional<UserRole> userRole = userRoleRepository.findByName(GenericEnum.RoleName.USER);
        return userRole.orElseGet(() -> userRoleRepository.save(new UserRole()));
    }
}

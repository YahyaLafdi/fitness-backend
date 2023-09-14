package com.metamafitness.fitnessbackend.repository;

import com.metamafitness.fitnessbackend.model.AppUserRole;
import com.metamafitness.fitnessbackend.model.GenericEnum;

import java.util.Optional;

public interface AppUserRoleRepository<R extends AppUserRole> extends GenericRepository<R>{

    public Optional<R> findByName(GenericEnum.RoleName roleName);


}

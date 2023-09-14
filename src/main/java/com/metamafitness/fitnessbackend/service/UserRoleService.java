package com.metamafitness.fitnessbackend.service;

import com.metamafitness.fitnessbackend.model.GenericEnum;
import com.metamafitness.fitnessbackend.model.UserRole;

public interface UserRoleService extends GenericService<UserRole> {


    UserRole findByName(GenericEnum.RoleName roleName) ;


}

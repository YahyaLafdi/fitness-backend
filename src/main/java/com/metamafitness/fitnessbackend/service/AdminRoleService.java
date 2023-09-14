package com.metamafitness.fitnessbackend.service;

import com.metamafitness.fitnessbackend.model.AdminRole;
import com.metamafitness.fitnessbackend.model.GenericEnum;

public interface AdminRoleService extends GenericService<AdminRole> {

    public AdminRole findByName(GenericEnum.RoleName roleName);
}

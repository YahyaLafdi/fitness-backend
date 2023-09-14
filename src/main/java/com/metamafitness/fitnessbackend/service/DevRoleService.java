package com.metamafitness.fitnessbackend.service;

import com.metamafitness.fitnessbackend.model.DevRole;
import com.metamafitness.fitnessbackend.model.GenericEnum;

public interface DevRoleService extends GenericService<DevRole> {

    public DevRole findByName(GenericEnum.RoleName roleName);
}

package com.metamafitness.fitnessbackend.service;

import com.metamafitness.fitnessbackend.model.GenericEnum;
import com.metamafitness.fitnessbackend.model.TrainerRole;

public interface TrainerRoleService extends GenericService<TrainerRole> {

    TrainerRole findByName(GenericEnum.RoleName roleName);
}

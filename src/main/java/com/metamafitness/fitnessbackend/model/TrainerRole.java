package com.metamafitness.fitnessbackend.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@Getter @Setter
@Builder
public class TrainerRole extends AppUserRole {

    public TrainerRole() {
        setName(GenericEnum.RoleName.TRAINER);
    }
}
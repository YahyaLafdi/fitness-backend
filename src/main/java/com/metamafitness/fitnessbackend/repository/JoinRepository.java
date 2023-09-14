package com.metamafitness.fitnessbackend.repository;

import com.metamafitness.fitnessbackend.model.Join;

import java.util.Optional;

public interface JoinRepository extends GenericRepository<Join>{

    Optional<Join> findBySender_id(Long id) ;
}

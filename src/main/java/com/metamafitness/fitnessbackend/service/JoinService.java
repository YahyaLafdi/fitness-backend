package com.metamafitness.fitnessbackend.service;

import com.metamafitness.fitnessbackend.model.Join;
import org.springframework.data.domain.Page;

public interface JoinService extends GenericService<Join> {

    Page<Join> findAll(int page, int size);

}

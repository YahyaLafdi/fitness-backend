package com.metamafitness.fitnessbackend.service;

import com.metamafitness.fitnessbackend.exception.BusinessException;
import com.metamafitness.fitnessbackend.exception.ElementAlreadyExistException;
import com.metamafitness.fitnessbackend.exception.ElementNotFoundException;
import com.metamafitness.fitnessbackend.model.GenericEntity;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GenericService<T extends GenericEntity> {

    public T update(final Long id, final T entity) throws ElementNotFoundException;

    public T findById(final Long id) throws ElementNotFoundException;

    public T save(final T entity) throws ElementAlreadyExistException;

    List<T> search(String keyword, Pageable pageable) throws BusinessException;
    public long countAll();

    public boolean delete(final Long id) throws ElementNotFoundException;

    // TODO add pagination + sort + filter
    public List<T> findAll() throws BusinessException;

    T patch(T entity) ;

    //public Page<T> findAll(final Pageable pageable, final List<Filter> filters);

}

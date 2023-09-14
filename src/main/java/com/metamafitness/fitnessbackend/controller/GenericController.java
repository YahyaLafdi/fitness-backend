package com.metamafitness.fitnessbackend.controller;


import com.metamafitness.fitnessbackend.common.CoreConstant;
import com.metamafitness.fitnessbackend.dto.GenericDto;
import com.metamafitness.fitnessbackend.dto.SearchDto;
import com.metamafitness.fitnessbackend.exception.BusinessException;
import com.metamafitness.fitnessbackend.exception.ElementNotFoundException;
import com.metamafitness.fitnessbackend.model.GenericEntity;
import com.metamafitness.fitnessbackend.model.User;
import com.metamafitness.fitnessbackend.service.GenericService;
import com.metamafitness.fitnessbackend.service.UserService;
import com.metamafitness.fitnessbackend.utils.ClassTypeProvider;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public abstract class GenericController<T extends GenericEntity, D extends GenericDto> {
    final Logger LOG = LoggerFactory.getLogger(GenericController.class);

    @Autowired
    private GenericService<T> genericService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ClassTypeProvider classTypeProvider;

    @Autowired
    private UserService userService;

    public ModelMapper getModelMapper() {
        return modelMapper;
    }

    protected Class<?>[] getClasses() {
        return classTypeProvider.getClasses(this, GenericController.class);
    }

    public D convertToDto(T entity) {
        return (D) getModelMapper().map(entity, getClasses()[1]);
    }

    public <S, D> void mapWithSkipNull(S sourceObject, D destinationObject) {
        // Save the original skipNullEnabled value
        boolean originalSkipNullEnabled = getModelMapper().getConfiguration().isSkipNullEnabled();

        // Set skipNullEnabled to true for this mapping operation
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        modelMapper.map(sourceObject, destinationObject);

        // Set skipNullEnabled back to its original value
        modelMapper.getConfiguration().setSkipNullEnabled(originalSkipNullEnabled);
    }

    public T convertToEntity(D dto) {
        return (T) getModelMapper().map(dto, getClasses()[0]);
    }




    protected Long getCurrentUserId() throws BusinessException {
        final Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();

        if(Objects.isNull(authentication.getPrincipal())){
            LOG.error(CoreConstant.Exception.AUTHENTICATION_NULL_PRINCIPAL);
            throw new BusinessException(null, new BusinessException(), CoreConstant.Exception.AUTHENTICATION_NULL_PRINCIPAL, null);
        }
        return (Long) authentication.getPrincipal();
    }

    protected User getCurrentUser() throws ElementNotFoundException {
        return userService.findById(getCurrentUserId());
    }


    @GetMapping("/{id}")
    public ResponseEntity<D> getById(@PathVariable("id") Long id) throws ElementNotFoundException {
        return new ResponseEntity<>(convertToDto(genericService.findById(id)), HttpStatus.OK);
    }

    @PostMapping("/search")
    public ResponseEntity<List<D>> search(@RequestBody SearchDto searchDto) throws BusinessException {
        searchDto.validate();
        Pageable pageable = PageRequest.of(searchDto.getPage(), searchDto.getSize());
        List<T> entities = genericService.search(searchDto.getKeyword(), pageable);
        List<D> dto = entities.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        long totalElements = genericService.countAll();
        int totalPages = (int) Math.ceil((double) totalElements / searchDto.getSize());

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Pages", String.valueOf(totalPages));
        headers.add("Access-Control-Expose-Headers", "X-Total-Pages");
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(dto);
    }

}

package org.community.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractService {

    protected final ModelMapper modelMapper;

    @Autowired
    protected AbstractService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    protected <D, E> D convertToDto(E entity, Class<D> dtoClass) {
        return modelMapper.map(entity, dtoClass);
    }

    protected <D, E> E convertToEntity(D dto, Class<E> entityClass) {
        return modelMapper.map(dto, entityClass);
    }
}
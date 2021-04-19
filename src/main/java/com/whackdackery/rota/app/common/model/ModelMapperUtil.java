package com.whackdackery.rota.app.common.model;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ModelMapperUtil<E extends BaseEntity, G extends GetDto, P extends PostDto> {

    private final ModelMapper modelMapper;

    @Autowired
    public ModelMapperUtil() {
        this.modelMapper = new ModelMapper();
    }

    public G entityToGetDto(E entity, Class<G> dtoClass) {
        return modelMapper.map(entity, dtoClass);
    }

    public E postDtoToEntity(P postDto, Class<E> entityClass) {
        return modelMapper.map(postDto, entityClass);
    }
}

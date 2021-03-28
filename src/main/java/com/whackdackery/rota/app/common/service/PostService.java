package com.whackdackery.rota.app.common.service;

import com.whackdackery.rota.app.common.model.BaseEntity;
import com.whackdackery.rota.app.common.model.GetDto;
import com.whackdackery.rota.app.common.model.PostDto;
import org.modelmapper.ModelMapper;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.Instant;
import java.util.Optional;

public abstract class PostService<E extends BaseEntity, P extends PostDto, G extends GetDto> {
    private final PagingAndSortingRepository<E, Long> repo;
    private final ModelMapper modelMapper;
    private final Class<E> entityClass;
    private final Class<G> getDtoClass;


    protected PostService(Class<E> entityClass, Class<G> getDtoClass, PagingAndSortingRepository<E, Long> repo, ModelMapper modelMapper) {
        this.repo = repo;
        this.modelMapper = modelMapper;
        this.entityClass = entityClass;
        this.getDtoClass = getDtoClass;
    }

    public Optional<G> add(P entityPostDto) {
        E entityFromDto = convertToEntity(entityPostDto);
        entityFromDto.setCreated(Instant.now());
        entityFromDto.setUpdated(Instant.now());
        E entity = repo.save(entityFromDto);
        return Optional.of(convertToGetDto(entity));
    }

    private E convertToEntity(P entityPostDto) {
        return modelMapper.map(entityPostDto, entityClass);
    }

    private G convertToGetDto(E entity) {
        return modelMapper.map(entity, getDtoClass);
    }

}

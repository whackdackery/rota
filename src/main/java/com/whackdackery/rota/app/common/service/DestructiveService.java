package com.whackdackery.rota.app.common.service;

import com.whackdackery.rota.app.common.model.BaseEntity;
import com.whackdackery.rota.app.common.model.GetDto;
import com.whackdackery.rota.app.common.model.PostDto;
import org.modelmapper.ModelMapper;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.Instant;
import java.util.Optional;

public abstract class DestructiveService<E extends BaseEntity, P extends PostDto, G extends GetDto> {
    private final PagingAndSortingRepository<E, Long> repo;
    private final ModelMapper modelMapper;
    private final Class<E> entityClass;
    private final Class<G> getDtoClass;


    protected DestructiveService(Class<E> entityClass, Class<G> getDtoClass, PagingAndSortingRepository<E, Long> repo, ModelMapper modelMapper) {
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

    public Optional<G> update(Long entityId, P entity, G existingEntity) {
        E newEntity = convertToEntity(entity);
        newEntity.setId(entityId);
        newEntity.setCreated(existingEntity.getCreated());
        newEntity.setUpdated(Instant.now());
        E savedEntity = repo.save(newEntity);
        return Optional.of(convertToGetDto(savedEntity));
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }

    private E convertToEntity(P entityPostDto) {
        return modelMapper.map(entityPostDto, entityClass);
    }

    private G convertToGetDto(E entity) {
        return modelMapper.map(entity, getDtoClass);
    }

}

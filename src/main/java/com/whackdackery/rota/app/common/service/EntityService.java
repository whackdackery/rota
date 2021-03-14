package com.whackdackery.rota.app.common.service;

import com.whackdackery.rota.app.common.model.BaseEntity;
import com.whackdackery.rota.app.common.model.GetDto;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public class EntityService<E extends BaseEntity, D extends GetDto> {
    private final PagingAndSortingRepository<E, Long> repo;
    private final ModelMapper modelMapper;
    private final Class<D> dtoClass;

    public EntityService(PagingAndSortingRepository<E, Long> repo,
                         ModelMapper modelMapper,
                         Class<D> dtoClass) {
        this.repo = repo;
        this.modelMapper = modelMapper;
        this.dtoClass = dtoClass;
    }


    public Optional<D> get(Long id) {
        Optional<E> entity = repo.findById(id);
        return entity.map(this::convertToDto);
    }

    public Page<D> getAll(Pageable pageable) {
        Page<E> entities = repo.findAll(pageable);
        return entities.map(this::convertToDto);
    }

    private D convertToDto(E entity) {
        return modelMapper.map(entity, dtoClass);
    }
}

package com.whackdackery.rota.app.common.service;

import com.whackdackery.rota.app.common.model.BaseEntity;
import com.whackdackery.rota.app.common.model.GetDto;
import com.whackdackery.rota.app.common.model.PostDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

public abstract class ServiceOrchestrator<E extends BaseEntity, G extends GetDto, P extends PostDto> {

    GetService<E, G> getService;
    DestructiveService<E, P, G> destructiveService;

    protected ServiceOrchestrator(GetService<E, G> getService, DestructiveService<E, P, G> destructiveService) {
        this.getService = getService;
        this.destructiveService = destructiveService;
    }

    @Transactional
    public Optional<G> getOne(Long id) {
        return getService.get(id);
    }

    @Transactional
    public Page<G> getAll(Pageable pageable) {
        return getService.getAll(pageable);
    }

    @Transactional
    public Optional<G> createOne(P entity) {
        return destructiveService.add(entity);
    }

    @Transactional
    public Optional<G> updateOne(Long entityId, P entity) {
        Optional<G> existingEntity = getService.get(entityId);
        if (existingEntity.isEmpty()) {
            throw new EntityNotFoundException();
        }
        return destructiveService.update(entityId, entity, existingEntity.get());
    }

    public void deleteOne(Long id) {
        destructiveService.delete(id);
    }
}

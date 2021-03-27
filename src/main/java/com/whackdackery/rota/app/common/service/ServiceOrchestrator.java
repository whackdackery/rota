package com.whackdackery.rota.app.common.service;

import com.whackdackery.rota.app.common.model.BaseEntity;
import com.whackdackery.rota.app.common.model.GetDto;
import com.whackdackery.rota.app.common.model.PostDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public abstract class ServiceOrchestrator<E extends BaseEntity, G extends GetDto, P extends PostDto> {

    GetService<E, G> getService;

    protected ServiceOrchestrator(GetService<E, G> getService) {
        this.getService = getService;
    }

    public Optional<G> getOne(Long id) {
        return getService.get(id);
    }

    public Page<G> getAll(Pageable pageable) {
        return getService.getAll(pageable);
    }

    public G createOne(P entity) {
        return null;
    }

    public G updateOne(P entity) {
        return null;
    }

    public void deleteOne(Long id) {

    }
}

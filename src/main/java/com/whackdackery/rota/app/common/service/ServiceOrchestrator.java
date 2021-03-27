package com.whackdackery.rota.app.common.service;

import com.whackdackery.rota.app.common.model.BaseEntity;
import com.whackdackery.rota.app.common.model.GetDto;
import com.whackdackery.rota.app.common.model.PostDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public abstract class ServiceOrchestrator<E extends BaseEntity, GD extends GetDto, PD extends PostDto, GS extends GetService<E, GD>> {

    GS getService;

    public Optional<GD> getOne(Long id) {
        return getService.get(id);
    }

    public Page<GD> getAll(Pageable pageable) {
        return getService.getAll(pageable);
    }

    public GD createOne(PD entity) {
        return null;
    }

    public GD updateOne(PD entity) {
        return null;
    }

    public void deleteOne(Long id) {

    }
}

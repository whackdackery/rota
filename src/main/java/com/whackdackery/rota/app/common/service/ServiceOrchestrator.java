package com.whackdackery.rota.app.common.service;

import com.whackdackery.rota.app.common.model.BaseEntity;
import com.whackdackery.rota.app.common.model.GetDto;
import com.whackdackery.rota.app.common.model.PostDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public abstract class ServiceOrchestrator<E extends BaseEntity, G extends GetDto, P extends PostDto> {

    GetService<E, G> getService;
    PostService<E, P, G> postService;

    protected ServiceOrchestrator(GetService<E, G> getService, PostService<E, P, G> postService) {
        this.getService = getService;
        this.postService = postService;
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
        return postService.add(entity);
    }

    public G updateOne(P entity) {
        return null;
    }

    public void deleteOne(Long id) {

    }
}

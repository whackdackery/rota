package com.whackdackery.rota.app.common.service;

import com.whackdackery.rota.app.common.model.GetDto;
import com.whackdackery.rota.app.common.model.PostDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ServiceOrchestrator<G extends GetDto, P extends PostDto> {

    public Optional<G> getOne(Long id);

    public Page<G> getAll(Pageable pageable);

    public Optional<G> createOne(P entity);

    public Optional<G> updateOne(Long entityId, P entity);

    public void deleteOne(Long id);

}

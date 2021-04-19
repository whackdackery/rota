package com.whackdackery.rota.app.common.service;

import com.whackdackery.rota.app.common.model.GetDto;
import com.whackdackery.rota.app.common.model.PostDto;

import java.util.Optional;

public interface DestructiveService<P extends PostDto, G extends GetDto> {

    public Optional<G> add(P entityPostDto);

    public Optional<G> update(Long entityId, P entity, G existingEntity);

    public void delete(Long id);
}

package com.whackdackery.rota.app.common.service;

import com.whackdackery.rota.app.common.model.GetDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface GetService<D extends GetDto> {

    public Optional<D> get(Long id);

    public Page<D> getAll(Pageable pageable);

}

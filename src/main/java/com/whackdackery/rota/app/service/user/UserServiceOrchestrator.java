package com.whackdackery.rota.app.service.user;

import com.whackdackery.rota.app.common.service.ServiceOrchestrator;
import com.whackdackery.rota.app.service.user.domain.dto.UserGetDto;
import com.whackdackery.rota.app.service.user.domain.dto.UserPostDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class UserServiceOrchestrator implements ServiceOrchestrator<UserGetDto, UserPostDto> {

    private final UserGetService getService;
    private final UserDestructiveService destructiveService;

    @Autowired
    public UserServiceOrchestrator(UserGetService getService, UserDestructiveService destructiveService) {
        this.getService = getService;
        this.destructiveService = destructiveService;
    }

    @Override
    public Optional<UserGetDto> getOne(Long id) {
        return getService.get(id);
    }

    @Override
    public Page<UserGetDto> getAll(Pageable pageable) {
        return getService.getAll(pageable);
    }

    @Override
    public Optional<UserGetDto> createOne(UserPostDto entity) {
        return destructiveService.add(entity);
    }

    @Override
    public Optional<UserGetDto> updateOne(Long entityId, UserPostDto entity) {
        Optional<UserGetDto> existingEntity = getService.get(entityId);
        if (existingEntity.isEmpty()) {
            throw new EntityNotFoundException();
        }
        return destructiveService.update(entityId, entity, existingEntity.get());
    }

    @Override
    public void deleteOne(Long id) {
        destructiveService.delete(id);
    }
}

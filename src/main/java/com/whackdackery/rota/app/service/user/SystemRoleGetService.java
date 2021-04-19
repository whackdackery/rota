package com.whackdackery.rota.app.service.user;

import com.whackdackery.rota.app.common.model.ModelMapperUtil;
import com.whackdackery.rota.app.common.service.GetService;
import com.whackdackery.rota.app.service.user.domain.SystemRole;
import com.whackdackery.rota.app.service.user.domain.dto.SystemRoleGetDto;
import com.whackdackery.rota.app.service.user.domain.dto.SystemRolePostDto;
import com.whackdackery.rota.app.service.user.repository.SystemRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class SystemRoleGetService implements GetService<SystemRoleGetDto> {

    private final ModelMapperUtil<SystemRole, SystemRoleGetDto, SystemRolePostDto> modelMapperUtil;
    private final SystemRoleRepository repo;

    @Autowired
    public SystemRoleGetService(ModelMapperUtil<SystemRole, SystemRoleGetDto, SystemRolePostDto> modelMapperUtil, SystemRoleRepository repo) {
        this.modelMapperUtil = modelMapperUtil;
        this.repo = repo;
    }

    @Override
    @Transactional
    public Optional<SystemRoleGetDto> get(Long id) {
        Optional<SystemRole> foundRole = repo.findById(id);
        return foundRole.map(roleGetDto -> modelMapperUtil.entityToGetDto(roleGetDto, SystemRoleGetDto.class));
    }

    @Override
    @Transactional
    public Page<SystemRoleGetDto> getAll(Pageable pageable) {
        Page<SystemRole> roles = repo.findAll(pageable);
        return roles.map(user -> modelMapperUtil.entityToGetDto(user, SystemRoleGetDto.class));
    }
}

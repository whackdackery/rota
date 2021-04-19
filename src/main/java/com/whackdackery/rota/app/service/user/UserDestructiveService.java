package com.whackdackery.rota.app.service.user;

import com.whackdackery.rota.app.common.model.ModelMapperUtil;
import com.whackdackery.rota.app.common.service.DestructiveService;
import com.whackdackery.rota.app.service.user.domain.SystemRole;
import com.whackdackery.rota.app.service.user.domain.User;
import com.whackdackery.rota.app.service.user.domain.dto.UserGetDto;
import com.whackdackery.rota.app.service.user.domain.dto.UserPostDto;
import com.whackdackery.rota.app.service.user.repository.SystemRoleRepository;
import com.whackdackery.rota.app.service.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

@Service
public class UserDestructiveService implements DestructiveService<UserPostDto, UserGetDto> {

    private final UserRepository repo;
    private final SystemRoleRepository systemRoleRepository;
    private final ModelMapperUtil<User, UserGetDto, UserPostDto> modelMapperUtil;

    public UserDestructiveService(UserRepository repo, SystemRoleRepository systemRoleRepository, ModelMapperUtil<User, UserGetDto, UserPostDto> modelMapperUtil) {
        this.repo = repo;
        this.systemRoleRepository = systemRoleRepository;
        this.modelMapperUtil = modelMapperUtil;
    }

    @Override
    public Optional<UserGetDto> add(UserPostDto newUser) {
        User userFromDto = modelMapperUtil.postDtoToEntity(newUser, User.class);
        userFromDto.setCreated(Instant.now());
        userFromDto.setUpdated(Instant.now());
        User savedUser = repo.save(userFromDto);
        return Optional.of(modelMapperUtil.entityToGetDto(savedUser, UserGetDto.class));
    }

    @Override
    public Optional<UserGetDto> update(Long userId, UserPostDto updatedUser, UserGetDto existingUser) {
        User newUser = modelMapperUtil.postDtoToEntity(updatedUser, User.class);
        newUser.setId(userId);
        newUser.setCreated(existingUser.getCreated());
        newUser.setUpdated(Instant.now());
        User savedUser = repo.save(newUser);
        return Optional.of(modelMapperUtil.entityToGetDto(savedUser, UserGetDto.class));
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }

    @Transactional
    public void addRoleForUser(Long userId, Long roleId) {
        Optional<User> user = repo.findById(userId);
        Optional<SystemRole> role = systemRoleRepository.findById(roleId);
        if (user.isPresent() && role.isPresent()) {
            user.get().getSystemRoles().add(role.get());
        }
    }
}

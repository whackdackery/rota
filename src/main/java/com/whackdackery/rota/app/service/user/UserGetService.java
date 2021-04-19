package com.whackdackery.rota.app.service.user;

import com.whackdackery.rota.app.common.model.ModelMapperUtil;
import com.whackdackery.rota.app.common.service.GetService;
import com.whackdackery.rota.app.service.user.domain.User;
import com.whackdackery.rota.app.service.user.domain.dto.UserGetDto;
import com.whackdackery.rota.app.service.user.domain.dto.UserPostDto;
import com.whackdackery.rota.app.service.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserGetService implements GetService<UserGetDto> {

    private final ModelMapperUtil<User, UserGetDto, UserPostDto> modelMapperUtil;
    private final UserRepository repo;

    @Autowired
    public UserGetService(ModelMapperUtil<User, UserGetDto, UserPostDto> modelMapperUtil, UserRepository repo) {
        this.modelMapperUtil = modelMapperUtil;
        this.repo = repo;
    }

    @Override
    public Optional<UserGetDto> get(Long id) {
        Optional<User> foundUser = repo.findById(id);
        return foundUser.map(user -> modelMapperUtil.entityToGetDto(user, UserGetDto.class));
    }

    @Override
    public Page<UserGetDto> getAll(Pageable pageable) {
        Page<User> users = repo.findAll(pageable);
        return users.map(user -> modelMapperUtil.entityToGetDto(user, UserGetDto.class));
    }
}

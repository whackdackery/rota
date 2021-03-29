package com.whackdackery.rota.app.user.service;

import com.whackdackery.rota.app.common.service.PostService;
import com.whackdackery.rota.app.user.model.User;
import com.whackdackery.rota.app.user.model.dto.UserGetDto;
import com.whackdackery.rota.app.user.model.dto.UserPostDto;
import com.whackdackery.rota.app.user.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class UserPostService extends PostService<User, UserPostDto, UserGetDto> {
    protected UserPostService(UserRepository repo, ModelMapper modelMapper) {
        super(User.class, UserGetDto.class, repo, modelMapper);
    }
}
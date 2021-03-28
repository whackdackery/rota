package com.whackdackery.rota.app.user.service;

import com.whackdackery.rota.app.common.service.GetService;
import com.whackdackery.rota.app.user.model.User;
import com.whackdackery.rota.app.user.model.dto.UserGetDto;
import com.whackdackery.rota.app.user.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class UserGetService extends GetService<User, UserGetDto> {

    public UserGetService(UserRepository repo, ModelMapper modelMapper) {
        super(repo, modelMapper, UserGetDto.class);
    }
}

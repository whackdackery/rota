package com.whackdackery.rota.app.user.service;

import com.whackdackery.rota.app.user.model.User;
import com.whackdackery.rota.app.user.model.dto.UserGetDto;
import com.whackdackery.rota.app.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    UserRepository userRepository;
    ModelMapper modelMapper;
    
    public Page<UserGetDto> getAll(Pageable pageable) {
        Page<User> users = userRepository.findAll(pageable);
        if (!users.isEmpty()){
            return users.map(this::convertToDto);
        }
        return Page.empty();
    }

    private UserGetDto convertToDto(User user) {
        return modelMapper.map(user, UserGetDto.class);
    }
}

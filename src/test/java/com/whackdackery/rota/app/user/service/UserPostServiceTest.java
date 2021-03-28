package com.whackdackery.rota.app.user.service;

import com.whackdackery.rota.app.user.model.User;
import com.whackdackery.rota.app.user.model.dto.UserGetDto;
import com.whackdackery.rota.app.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static com.whackdackery.rota.app.user.service.UserTestSetups.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserPostServiceTest {
    @Mock
    UserRepository repo;
    @Spy
    ModelMapper modelMapper;

    @InjectMocks
    UserPostService service;

    @Test
    void returnsResultWhenUserSuccessfullyInserted() {
        User userExtractedFromPostDto = modelMapper.map(getTestUserOnePostDto(), User.class);
        when(repo.save(userExtractedFromPostDto)).thenReturn(getTestUserOne());

        Optional<UserGetDto> createdUser = service.add(getTestUserOnePostDto());
        assertThat(createdUser.get()).isEqualTo(getTestUserOneGetDto());
    }
}
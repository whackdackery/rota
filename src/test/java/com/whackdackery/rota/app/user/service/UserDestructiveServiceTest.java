package com.whackdackery.rota.app.user.service;

import com.whackdackery.rota.app.user.model.dto.UserGetDto;
import com.whackdackery.rota.app.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;

import static com.whackdackery.rota.app.user.service.UserTestSetups.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserDestructiveServiceTest {
    @Mock
    UserRepository repo;
    @Spy
    ModelMapper modelMapper;

    @InjectMocks
    UserDestructiveService service;

    @Test
    void returnsResultWhenUserSuccessfullyInserted() {
        when(repo.save(any()))
                .thenReturn(getTestUserOne());

        Optional<UserGetDto> createdUser = service.add(getTestUserOnePostDto());
        assertThat(createdUser).contains(getTestUserOneGetDto());
    }

    @Test
    void returnsErrorWhenUserAlreadyExists() {
        when(repo.save(any()))
                .thenReturn(getTestUserOne())
                .thenThrow(DataIntegrityViolationException.class);

        service.add(getTestUserOnePostDto());
        assertThatThrownBy(() -> service.add(getTestUserOnePostDto())).isInstanceOf(DataIntegrityViolationException.class);
    }
}
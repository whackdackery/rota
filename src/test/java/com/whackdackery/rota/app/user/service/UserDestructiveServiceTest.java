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

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

import static com.whackdackery.rota.app.user.UserTestSetups.*;
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
                .thenReturn(superAdminUserIdOne());

        Optional<UserGetDto> createdUser = service.add(superAdminUserOnePostDto());
        assertThat(createdUser).contains(superAdminUserOneGetDto());
    }

    @Test
    void returnsErrorWhenUserAlreadyExistsDuringUserPost() {
        when(repo.save(any()))
                .thenReturn(superAdminUserIdOne())
                .thenThrow(DataIntegrityViolationException.class);

        service.add(superAdminUserOnePostDto());
        assertThatThrownBy(() -> service.add(superAdminUserOnePostDto())).isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    void returnsResultWhenUserSuccessfullyUpdatedDuringUserPut() {
        when(repo.save(any()))
                .thenReturn(superAdminUserIdOne());

        Optional<UserGetDto> updatedUser = service.update(1L, superAdminUserOnePostDto(), superAdminUserOneGetDto());
        assertThat(updatedUser).contains(superAdminUserOneGetDto());
    }

    @Test
    void returnsErrorWhenUserDoesNotExistDuringUserPut() {
        when(repo.save(any()))
                .thenReturn(superAdminUserIdOne())
                .thenThrow(EntityNotFoundException.class);

        service.update(1L, superAdminUserOnePostDto(), superAdminUserOneGetDto());
        assertThatThrownBy(() -> service.update(1L, superAdminUserOnePostDto(), superAdminUserOneGetDto())).isInstanceOf(EntityNotFoundException.class);
    }

}
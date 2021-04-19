package com.whackdackery.rota.app.service.user;

import com.whackdackery.rota.app.common.model.ModelMapperUtil;
import com.whackdackery.rota.app.service.user.domain.User;
import com.whackdackery.rota.app.service.user.domain.dto.UserGetDto;
import com.whackdackery.rota.app.service.user.domain.dto.UserPostDto;
import com.whackdackery.rota.app.service.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

import static com.whackdackery.rota.app.service.user.UserTestSetups.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserDestructiveServiceTest {

    @Mock
    UserRepository repo;
    @Spy
    ModelMapperUtil<User, UserGetDto, UserPostDto> modelMapperUtil;
    @InjectMocks
    UserDestructiveService service;

    @Test
    void returnsResultWhenUserSuccessfullyInserted() {
        when(repo.save(any()))
                .thenReturn(superAdminUserOne());

        Optional<UserGetDto> createdUser = service.add(superAdminUserOnePostDto());
        assertThat(createdUser).contains(superAdminUserOneGetDto());
    }

    @Test
    void returnsErrorWhenUserAlreadyExistsDuringUserPost() {
        when(repo.save(any()))
                .thenReturn(superAdminUserOne())
                .thenThrow(DataIntegrityViolationException.class);

        service.add(superAdminUserOnePostDto());
        assertThatThrownBy(() -> service.add(superAdminUserOnePostDto())).isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    void returnsResultWhenUserSuccessfullyUpdatedDuringUserPut() {
        when(repo.save(any()))
                .thenReturn(superAdminUserOne());

        Optional<UserGetDto> updatedUser = service.update(1L, superAdminUserOnePostDto(), superAdminUserOneGetDto());
        assertThat(updatedUser).contains(superAdminUserOneGetDto());
    }

    @Test
    void returnsErrorWhenUserDoesNotExistDuringUserPut() {
        when(repo.save(any()))
                .thenReturn(superAdminUserOne())
                .thenThrow(EntityNotFoundException.class);

        service.update(1L, superAdminUserOnePostDto(), superAdminUserOneGetDto());
        assertThatThrownBy(() -> service.update(1L, superAdminUserOnePostDto(), superAdminUserOneGetDto())).isInstanceOf(EntityNotFoundException.class);
    }

}
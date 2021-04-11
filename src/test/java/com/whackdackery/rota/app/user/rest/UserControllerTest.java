package com.whackdackery.rota.app.user.rest;

import com.whackdackery.rota.app.user.model.dto.UserGetDto;
import com.whackdackery.rota.app.user.service.UserServiceOrchestrator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static com.whackdackery.rota.app.user.UserTestSetups.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    UserServiceOrchestrator orchestrator;
    @InjectMocks
    UserController controller;

    @Test
    void getUserReturnsSingleResult() {
        when(orchestrator.getOne(SUPER_ADMIN_USER_ID)).thenReturn(Optional.of(superAdminUserOneGetDto()));

        ResponseEntity<UserGetDto> user = controller.getUser(SUPER_ADMIN_USER_ID);
        assertThat(user.getBody()).isEqualTo(superAdminUserOneGetDto());
    }

    @Test
    void getUserReturnsThrowsNotFoundException() {
        when(orchestrator.getOne(any())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> controller.getUser(SUPER_ADMIN_USER_ID)).isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Not found");
    }

    @Test
    void getUsersReturnsMultipleResults() {
        when(orchestrator.getAll(any(Pageable.class))).thenReturn(getPageContainingMultipleUserDtos());

        Page<UserGetDto> users = controller.getUsers(Pageable.unpaged());
        assertThat(users).isEqualTo(getPageContainingMultipleUserDtos());
    }

    @Test
    void getUsersReturnsNoResults() {
        when(orchestrator.getAll(any(Pageable.class))).thenReturn(Page.empty());

        assertThatThrownBy(() -> controller.getUsers(Pageable.unpaged())).isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Not found");
    }

    @Test
    void postUserReturnsSuccessfulResult() {
        when(orchestrator.createOne(superAdminUserOnePostDto())).thenReturn(Optional.of(superAdminUserOneGetDto()));

        ResponseEntity<UserGetDto> userGetDtoResponseEntity = controller.create(superAdminUserOnePostDto());
        assertThat(userGetDtoResponseEntity.getBody()).isEqualTo(superAdminUserOneGetDto());
    }

    @Test
    void putUserReturnsSingleResult() {
        when(orchestrator.updateOne(SUPER_ADMIN_USER_ID, superAdminUserOnePostDto())).thenReturn(Optional.of(superAdminUserOneGetDto()));

        ResponseEntity<UserGetDto> user = controller.update(SUPER_ADMIN_USER_ID, superAdminUserOnePostDto());
        assertThat(user.getBody()).isEqualTo(superAdminUserOneGetDto());
    }

    @Test
    void putUserUpdatesExistingUser() {
        UserGetDto updatedUser = superAdminUserOneGetDto();
        updatedUser.setUsername("UPDATED");
        when(orchestrator.updateOne(any(), any())).thenReturn(Optional.of(updatedUser));

        ResponseEntity<UserGetDto> user = controller.update(SUPER_ADMIN_USER_ID, superAdminUserOnePostDto());
        assertThat(user.getBody()).isEqualTo(updatedUser);
    }

    @Test
    void deleteUserSuccessfully() {
        assertThatCode(() -> controller.delete(1L)).doesNotThrowAnyException();
    }

    @Test
    void deleteUserFails() {
        doThrow(EmptyResultDataAccessException.class).when(orchestrator).deleteOne(1L);
        assertThatThrownBy(() -> controller.delete(1L)).isInstanceOf(EmptyResultDataAccessException.class);
    }
}
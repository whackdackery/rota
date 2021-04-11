package com.whackdackery.rota.app.user.service;

import com.whackdackery.rota.app.user.model.dto.UserGetDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

import static com.whackdackery.rota.app.user.UserTestSetups.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceOrchestratorTest {

    @Mock
    UserGetService getService;
    @Mock
    UserDestructiveService destructiveService;
    @InjectMocks
    UserServiceOrchestrator orchestrator;

    @Test
    void returnsEmptyOptionalIfNoUserFound() {
        when(getService.get(any())).thenReturn(Optional.empty());

        Optional<UserGetDto> user = orchestrator.getOne(100L);
        assertThat(user).isNotPresent();
    }

    @Test
    void returnsResultIfUserFound() {
        when(getService.get(SUPER_ADMIN_USER_ID)).thenReturn(Optional.of(superAdminUserOneGetDto()));

        Optional<UserGetDto> user = orchestrator.getOne(SUPER_ADMIN_USER_ID);
        assertThat(user).isPresent();
    }

    @Test
    void returnsMultipleResultsIfMultipleUsersFound() {
        when(getService.getAll(any(Pageable.class))).thenReturn(getPageContainingMultipleUserDtos());

        Page<UserGetDto> userPage = orchestrator.getAll(Pageable.unpaged());
        assertThat(userPage.getTotalElements()).isEqualTo(2L);
    }

    @Test
    void returnsUserIfCreatedSuccessfully() {
        when(destructiveService.add(superAdminUserOnePostDto())).thenReturn(Optional.of(superAdminUserOneGetDto()));

        Optional<UserGetDto> user = orchestrator.createOne(superAdminUserOnePostDto());
        assertThat(user).isPresent();
        assertThat(user.get()).isEqualTo(superAdminUserOneGetDto());
    }

    @Test
    void returnsUserIfUpdatedSuccessfully() {
        when(getService.get(any())).thenReturn(Optional.of(superAdminUserOneGetDto()));
        when(destructiveService.update(1L, superAdminUserOnePostDto(), superAdminUserOneGetDto())).thenReturn(Optional.of(superAdminUserOneGetDto()));

        Optional<UserGetDto> user = orchestrator.updateOne(1L, superAdminUserOnePostDto());
        assertThat(user).isPresent();
        assertThat(user.get()).isEqualTo(superAdminUserOneGetDto());
    }

    @Test
    void returnsErrorDuringUpdateIfUserDoesNotExist() {
        when(getService.get(any())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> orchestrator.updateOne(1L, superAdminUserOnePostDto())).isInstanceOf(EntityNotFoundException.class);
    }
}
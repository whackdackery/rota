package com.whackdackery.rota.app.user.service;

import com.whackdackery.rota.app.user.model.dto.UserGetDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceOrchestratorTest {

    @Mock
    UserGetService getService;
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
        when(getService.get(UserTestSetup.TEST_ID_ONE)).thenReturn(Optional.of(UserTestSetup.getTestUserOneDto()));

        Optional<UserGetDto> user = orchestrator.getOne(UserTestSetup.TEST_ID_ONE);
        assertThat(user).isPresent();
    }

    @Test
    void returnsMultipleResultsIfMultipleUsersFound() {
        when(getService.getAll(any(Pageable.class))).thenReturn(UserTestSetup.getPageContainingMultipleUserDtos());

        Page<UserGetDto> userPage = orchestrator.getAll(Pageable.unpaged());
        assertThat(userPage.getTotalElements()).isEqualTo(2L);
    }
}
package com.whackdackery.rota.app.user.service;

import com.whackdackery.rota.app.user.model.SystemRole;
import com.whackdackery.rota.app.user.model.dto.UserGetDto;
import com.whackdackery.rota.app.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static com.whackdackery.rota.app.user.UserTestSetups.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserGetServiceTest {

    @Mock
    UserRepository userRepositoryMock;
    @Spy
    ModelMapper modelMapper;
    @InjectMocks
    UserGetService userGetService;

    @Test
    void getReturnsDtoWhenIdIsValid() {
        when(userRepositoryMock.findById(SUPER_ADMIN_USER_ID)).thenReturn(Optional.of(superAdminUserIdOne()));

        Optional<UserGetDto> user = userGetService.get(SUPER_ADMIN_USER_ID);
        assertThat(user).isPresent();
        assertThat(user.get()).isInstanceOf(UserGetDto.class);
        assertThat(user.get().getRoles().stream().findFirst().get().getRoleType()).isEqualTo(SystemRole.RoleType.SUPER_ADMIN);
    }

    @Test
    void getReturnsDtoWhenIdIsInvalid() {
        when(userRepositoryMock.findById(ADMIN_USER_ID)).thenReturn(Optional.empty());

        Optional<UserGetDto> user = userGetService.get(ADMIN_USER_ID);
        assertThat(user).isNotPresent();
    }

    @Test
    void getAllReturnsPageWithSingleDto() {
        when(userRepositoryMock.findAll(Pageable.unpaged())).thenReturn(getPageContainingSingleUser());

        Page<UserGetDto> users = userGetService.getAll(Pageable.unpaged());
        UserGetDto user = users.get().findFirst().get();
        assertThat(users.getTotalElements()).isEqualTo(1L);
        assertThat(users).isInstanceOf(Page.class);
        assertThat(user).isEqualTo(superAdminUserOneGetDto());
    }

    @Test
    void getAllReturnsPageWithMultipleDtos() {
        when(userRepositoryMock.findAll(any(Pageable.class))).thenReturn(getPageContainingMultipleUsers());

        Page<UserGetDto> users = userGetService.getAll(Pageable.unpaged());
        assertThat(users.getTotalElements()).isEqualTo(2L);
        assertThat(users).isInstanceOf(Page.class).isEqualTo(getPageContainingMultipleUserDtos());
    }

    @Test
    void getAllReturnsEmptyPageWhenNoResultsFound() {
        when(userRepositoryMock.findAll(any(Pageable.class))).thenReturn(Page.empty());

        Page<UserGetDto> users = userGetService.getAll(Pageable.unpaged());
        assertThat(users.getTotalElements()).isZero();
        assertThat(users).isInstanceOf(Page.class);
    }
}
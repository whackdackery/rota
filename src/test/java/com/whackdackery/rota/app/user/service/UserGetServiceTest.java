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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static com.whackdackery.rota.app.user.service.UserTestSetup.getTestUserOne;
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
        when(userRepositoryMock.findById(UserTestSetup.TEST_ID_ONE)).thenReturn(Optional.of(getTestUserOne()));

        Optional<UserGetDto> user = userGetService.get(UserTestSetup.TEST_ID_ONE);
        assertThat(user).isPresent();
        assertThat(user.get()).isInstanceOf(UserGetDto.class);
    }

    @Test
    void getReturnsDtoWhenIdIsInvalid() {
        when(userRepositoryMock.findById(2L)).thenReturn(Optional.empty());

        Optional<UserGetDto> user = userGetService.get(2L);
        assertThat(user).isNotPresent();
    }

    @Test
    void getAllReturnsPageWithSingleDto() {
        when(userRepositoryMock.findAll(Pageable.unpaged())).thenReturn(UserTestSetup.getPageContainingSingleUser());

        Page<UserGetDto> users = userGetService.getAll(Pageable.unpaged());
        UserGetDto user = users.get().findFirst().get();
        assertThat(users.getTotalElements()).isEqualTo(1L);
        assertThat(users).isInstanceOf(Page.class);
        assertThat(user.getUsername()).isEqualTo(UserTestSetup.TEST_USERNAME_ONE);
        assertThat(user.getEmail()).isEqualTo(UserTestSetup.TEST_EMAIL_ONE);
        assertThat(user.getId()).isEqualTo(1L);
        assertThat(user.getCreated()).isEqualTo(UserTestSetup.CREATED_ON);
        assertThat(user.getUpdated()).isEqualTo(UserTestSetup.UPDATED_ON);
    }

    @Test
    void getAllReturnsPageWithMultipleDtos() {
        when(userRepositoryMock.findAll(any(Pageable.class))).thenReturn(UserTestSetup.getPageContainingMultipleUsers());

        Page<UserGetDto> users = userGetService.getAll(Pageable.unpaged());
        assertThat(users.getTotalElements()).isEqualTo(2L);
        assertThat(users).isInstanceOf(Page.class);
        UserGetDto user = users.get().findFirst().get();
        assertThat(user.getUsername()).isEqualTo(UserTestSetup.TEST_USERNAME_ONE);
        assertThat(users.toList().get(1).getUsername()).isEqualTo(UserTestSetup.TEST_USERNAME_TWO);
    }

    @Test
    void getAllReturnsEmptyPageWhenNoResultsFound() {
        when(userRepositoryMock.findAll(any(Pageable.class))).thenReturn(Page.empty());

        Page<UserGetDto> users = userGetService.getAll(Pageable.unpaged());
        assertThat(users.getTotalElements()).isZero();
        assertThat(users).isInstanceOf(Page.class);
    }
}
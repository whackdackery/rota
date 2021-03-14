package com.whackdackery.rota.app.user.service;

import com.whackdackery.rota.app.user.model.User;
import com.whackdackery.rota.app.user.model.dto.UserGetDto;
import com.whackdackery.rota.app.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class UserServiceTest {

    private static final String TEST_USERNAME_ONE = "Test";
    private static final String TEST_EMAIL_ONE = "test@admin.com";
    private static final String TEST_USERNAME_TWO = "Test2";
    private static final String TEST_EMAIL_TWO = "test2@admin.com";
    private static final Instant CREATED_ON = Instant.parse("2020-01-01T09:00:00.00Z");
    private static final Instant UPDATED_ON = Instant.parse("2020-01-01T09:00:00.00Z");

    @Autowired
    UserService userService;
    @MockBean
    private UserRepository userRepositoryMock;

    @Test
    void getReturnsDtoWhenIdIsValid() {
        when(userRepositoryMock.findById(1L)).thenReturn(Optional.of(getTestUserOne()));

        Optional<UserGetDto> user = userService.get(1L);
        assertThat(user).isPresent();
        assertThat(user.get()).isInstanceOf(UserGetDto.class);
    }

    @Test
    void getReturnsDtoWhenIdIsInvalid() {
        when(userRepositoryMock.findById(2L)).thenReturn(Optional.empty());

        Optional<UserGetDto> user = userService.get(2L);
        assertThat(user).isNotPresent();
    }

    @Test
    void getAllReturnsPageWithSingleDto() {
        when(userRepositoryMock.findAll(Pageable.unpaged())).thenReturn(getPageContainingSingleUser());

        Page<UserGetDto> users = userService.getAll(Pageable.unpaged());
        assertThat(users.getTotalElements()).isEqualTo(1L);
        assertThat(users).isInstanceOf(Page.class);
        assertThat(users.get().findFirst().get().getUsername()).isEqualTo(TEST_USERNAME_ONE);
    }

    @Test
    void getAllReturnsPageWithMultipleDtos() {
        when(userRepositoryMock.findAll(Pageable.unpaged())).thenReturn(getPageContainingMultipleUsers());

        Page<UserGetDto> users = userService.getAll(Pageable.unpaged());
        assertThat(users.getTotalElements()).isEqualTo(2L);
        assertThat(users).isInstanceOf(Page.class);
        assertThat(users.get().findFirst().get().getUsername()).isEqualTo(TEST_USERNAME_ONE);
        assertThat(users.toList().get(1).getUsername()).isEqualTo(TEST_USERNAME_TWO);
    }

    @Test
    void getAllReturnsEmptyPageWhenNoResultsFound() {
        when(userRepositoryMock.findAll(Pageable.unpaged())).thenReturn(Page.empty());

        Page<UserGetDto> users = userService.getAll(Pageable.unpaged());
        assertThat(users.getTotalElements()).isZero();
        assertThat(users).isInstanceOf(Page.class);
    }

    private User getTestUserOne() {
        User user = new User();
        user.setUsername(TEST_USERNAME_ONE);
        user.setEmail(TEST_EMAIL_ONE);
        user.setCreated(CREATED_ON);
        user.setUpdated(UPDATED_ON);
        return user;
    }

    private User getTestUserTwo() {
        User user = new User();
        user.setUsername(TEST_USERNAME_TWO);
        user.setEmail(TEST_EMAIL_TWO);
        user.setCreated(CREATED_ON);
        user.setUpdated(UPDATED_ON);
        return user;
    }

    private PageImpl<User> getPageContainingSingleUser() {
        return new PageImpl<>(List.of(getTestUserOne()));
    }

    private PageImpl<User> getPageContainingMultipleUsers() {
        return new PageImpl<>(List.of(getTestUserOne(), getTestUserTwo()));
    }


}
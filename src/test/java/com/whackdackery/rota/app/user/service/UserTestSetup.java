package com.whackdackery.rota.app.user.service;

import com.whackdackery.rota.app.user.model.User;
import com.whackdackery.rota.app.user.model.dto.UserGetDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class UserTestSetup {

    public static final Long TEST_ID_ONE = 1L;
    public static final Long TEST_ID_TWO = 2L;
    public static final String TEST_USERNAME_ONE = "Test";
    public static final String TEST_EMAIL_ONE = "test@admin.com";
    public static final String TEST_PASSWORD_ONE = "password1";
    public static final String TEST_USERNAME_TWO = "Test2";
    public static final String TEST_EMAIL_TWO = "test2@admin.com";
    public static final String TEST_PASSWORD_TWO = "password2";
    public static final Instant CREATED_ON = Instant.parse("2020-01-01T09:00:00.00Z");
    public static final Instant UPDATED_ON = Instant.parse("2020-01-01T09:00:00.00Z");

    public static User getTestUserOne() {
        return generateUser(TEST_ID_ONE, TEST_USERNAME_ONE, TEST_EMAIL_ONE, TEST_PASSWORD_ONE);
    }

    public static User getTestUserTwo() {
        return generateUser(TEST_ID_TWO, TEST_USERNAME_TWO, TEST_EMAIL_TWO, TEST_PASSWORD_TWO);
    }

    private static User generateUser(Long testIdTwo, String testUsernameTwo, String testEmailTwo, String testPasswordTwo) {
        User user = new User();
        user.setId(testIdTwo);
        user.setUsername(testUsernameTwo);
        user.setEmail(testEmailTwo);
        user.setPassword(testPasswordTwo);
        user.setCreated(CREATED_ON);
        user.setUpdated(UPDATED_ON);
        return user;
    }

    public static UserGetDto getTestUserOneDto() {
        User user = getTestUserOne();
        return generateDto(user);
    }

    public static UserGetDto getTestUserTwoDto() {
        User user = getTestUserTwo();
        return generateDto(user);
    }

    private static UserGetDto generateDto(User user) {
        UserGetDto userGetDto = new UserGetDto();
        userGetDto.setId(user.getId());
        userGetDto.setUsername(user.getUsername());
        userGetDto.setEmail(user.getEmail());
        userGetDto.setCreated(user.getCreated());
        userGetDto.setUpdated(user.getUpdated());
        return userGetDto;
    }

    public static Page<User> getPageContainingSingleUser() {
        List<User> users = new ArrayList<>();
        users.add(getTestUserOne());
        return new PageImpl<>(users);
    }

    public static Page<User> getPageContainingMultipleUsers() {
        List<User> users = new ArrayList<>();
        users.add(getTestUserOne());
        users.add(getTestUserTwo());
        return new PageImpl<>(users);
    }

    public static Page<UserGetDto> getPageContainingSingleUserDto() {
        List<UserGetDto> users = new ArrayList<>();
        users.add(getTestUserOneDto());
        return new PageImpl<>(users);
    }

    public static Page<UserGetDto> getPageContainingMultipleUserDtos() {
        List<UserGetDto> users = new ArrayList<>();
        users.add(getTestUserOneDto());
        users.add(getTestUserTwoDto());
        return new PageImpl<>(users);
    }

}

package com.whackdackery.rota.app.service.user;

import com.whackdackery.rota.app.service.user.domain.SystemRole;
import com.whackdackery.rota.app.service.user.domain.User;
import com.whackdackery.rota.app.service.user.domain.dto.UserGetDto;
import com.whackdackery.rota.app.service.user.domain.dto.UserPostDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UserTestSetups {

    public static final Long SUPER_ADMIN_USER_ID = 1L;
    public static final String SUPER_ADMIN_USERNAME = "Test";
    public static final String SUPER_ADMIN_EMAIL = "test@admin.com";
    public static final String SUPER_ADMIN_PASSWORD = "password1";
    public static final Long ADMIN_USER_ID = 2L;
    public static final String ADMIN_USERNAME = "Test2";
    public static final String ADMIN_EMAIL = "test2@admin.com";
    public static final String ADMIN_PASSWORD = "password2";
    public static final Instant CREATED_ON = Instant.parse("2020-01-01T09:00:00.00Z");
    public static final Instant UPDATED_ON = Instant.parse("2020-01-01T09:00:00.00Z");

    public static User superAdminUserOne() {
        return generateUser(SUPER_ADMIN_USER_ID, SUPER_ADMIN_USERNAME, SUPER_ADMIN_EMAIL, SUPER_ADMIN_PASSWORD, SystemRole.RoleType.SUPER_ADMIN);
    }

    public static User adminUserTwo() {
        return generateUser(ADMIN_USER_ID, ADMIN_USERNAME, ADMIN_EMAIL, ADMIN_PASSWORD, SystemRole.RoleType.ADMIN);
    }

    private static User generateUser(Long testIdTwo, String testUsernameTwo, String testEmailTwo, String testPasswordTwo, SystemRole.RoleType roleType) {
        User user = new User();
        user.setId(testIdTwo);
        user.setUsername(testUsernameTwo);
        user.setEmail(testEmailTwo);
        user.setPassword(testPasswordTwo);
        user.setCreated(CREATED_ON);
        user.setUpdated(UPDATED_ON);
        SystemRole systemRole = new SystemRole();
        systemRole.setRoleType(roleType);
        systemRole.setName(roleType.name());
        user.setSystemRoles(Collections.singleton(systemRole));
        return user;
    }

    public static UserGetDto superAdminUserOneGetDto() {
        User user = superAdminUserOne();
        return generateGetDto(user);
    }

    public static UserGetDto adminUserTwoGetDto() {
        User user = adminUserTwo();
        return generateGetDto(user);
    }

    private static UserGetDto generateGetDto(User user) {
        UserGetDto userGetDto = new UserGetDto();
        userGetDto.setId(user.getId());
        userGetDto.setUsername(user.getUsername());
        userGetDto.setEmail(user.getEmail());
        userGetDto.setCreated(user.getCreated());
        userGetDto.setUpdated(user.getUpdated());
        userGetDto.setRoles(user.getSystemRoles());
        return userGetDto;
    }

    public static Page<User> getPageContainingSingleUser() {
        List<User> users = new ArrayList<>();
        users.add(superAdminUserOne());
        return new PageImpl<>(users);
    }

    public static Page<User> getPageContainingMultipleUsers() {
        List<User> users = new ArrayList<>();
        users.add(superAdminUserOne());
        users.add(adminUserTwo());
        return new PageImpl<>(users);
    }

    public static Page<UserGetDto> getPageContainingSingleUserDto() {
        List<UserGetDto> users = new ArrayList<>();
        users.add(superAdminUserOneGetDto());
        return new PageImpl<>(users);
    }

    public static Page<UserGetDto> getPageContainingMultipleUserDtos() {
        List<UserGetDto> users = new ArrayList<>();
        users.add(superAdminUserOneGetDto());
        users.add(adminUserTwoGetDto());
        return new PageImpl<>(users);
    }

    public static UserPostDto superAdminUserOnePostDto() {
        User user = superAdminUserOne();
        return generatePostDto(user);
    }

    private static UserPostDto generatePostDto(User user) {
        return UserPostDto.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .password(user.getPassword())
                .build();
    }


}

package com.whackdackery.rota.app.user.rest;

import com.whackdackery.rota.app.user.model.dto.UserGetDto;
import com.whackdackery.rota.app.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;


@RestController
@RequestMapping(path = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class UserController {

    UserService userService;

    @GetMapping
    public Page<UserGetDto> getUsers(Pageable pageable) {
        Page<UserGetDto> users = userService.getAll(pageable);
        if (users.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        }
        return users;
    }

    @GetMapping(path = "/{userId}")
    public UserGetDto getUser(@PathVariable Long userId) {
        Optional<UserGetDto> user = userService.get(userId);
        if (user.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        }
        return user.get();
    }
}

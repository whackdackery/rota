package com.whackdackery.rota.app.user.rest;

import com.whackdackery.rota.app.user.model.dto.UserGetDto;
import com.whackdackery.rota.app.user.model.dto.UserPostDto;
import com.whackdackery.rota.app.user.service.UserServiceOrchestrator;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping(path = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class UserController {

    UserServiceOrchestrator orchestrator;

    @GetMapping(path = "/{userId}")
    public ResponseEntity<UserGetDto> getUser(@PathVariable Long userId) {
        Optional<UserGetDto> user = orchestrator.getOne(userId);
        if (user.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        }
        return new ResponseEntity<>(user.get(), HttpStatus.OK);
    }

    @GetMapping
    public Page<UserGetDto> getUsers(Pageable pageable) {
        Page<UserGetDto> users = orchestrator.getAll(pageable);
        if (users.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        }
        return users;
    }

    @PostMapping
    public ResponseEntity<UserGetDto> create(@Valid @RequestBody UserPostDto user) {
        Optional<UserGetDto> createdUser = orchestrator.createOne(user);
        if (createdUser.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "There was a problem saving this user");
        }
        return new ResponseEntity<>(createdUser.get(), HttpStatus.CREATED);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public Map<String, String> handleSqlViolationExceptions() {
        Map<String, String> errors = new HashMap<>();
        errors.put("Error", "Username or email already exists");
        return errors;
    }
}

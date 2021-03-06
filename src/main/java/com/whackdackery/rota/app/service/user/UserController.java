package com.whackdackery.rota.app.service.user;

import com.whackdackery.rota.app.service.user.domain.dto.UserGetDto;
import com.whackdackery.rota.app.service.user.domain.dto.UserPostDto;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
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
        return new ResponseEntity<>(createdUser.get(), HttpStatus.CREATED);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserGetDto> update(@PathVariable Long userId, @Valid @RequestBody UserPostDto user) {
        Optional<UserGetDto> updatedUser = orchestrator.updateOne(userId, user);
        return new ResponseEntity<>(updatedUser.get(), HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public void delete(@PathVariable Long userId) {
        orchestrator.deleteOne(userId);
    }

    @PostMapping("/{userId}/role/{roleId}")
    public void addRoleForUser(@PathVariable Long userId, @PathVariable Long roleId) {
//        orchestrator.addRoleForUser(userId, roleId);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public Map<String, String> handleSqlViolationExceptions() {
        return buildErrorMap("Username or email already exists");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(EmptyResultDataAccessException.class)
    public Map<String, String> handleEmptyResultDataAccessExceptions() {
        return buildErrorMap("User does not exist. Nothing deleted");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(EntityNotFoundException.class)
    public Map<String, String> handleEntityNotFoundExceptions() {
        return buildErrorMap("User does not exist");
    }

    private Map<String, String> buildErrorMap(String s) {
        Map<String, String> errors = new HashMap<>();
        errors.put("Error", s);
        return errors;
    }
}

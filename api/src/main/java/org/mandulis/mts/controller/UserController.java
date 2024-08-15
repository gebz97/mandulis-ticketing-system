package org.mandulis.mts.controller;

import jakarta.validation.Valid;
import org.mandulis.mts.dto.request.CreateOrUpdateUserRequest;
import org.mandulis.mts.exception.UserAlreadyExistsException;
import org.mandulis.mts.exception.UserNotFoundException;
import org.mandulis.mts.exception.UserValidationException;
import org.mandulis.mts.service.UserService;
import org.mandulis.mts.dto.response.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/public/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(userService.findAllUsers());
    }

    @PostMapping("/")
    public ResponseEntity<?> createUser(@Valid @RequestBody CreateOrUpdateUserRequest request) {
        try {
            UserResponse createdUser = userService.saveUser(request);
            return ResponseEntity.ok(createdUser);
        }catch (Exception e) {
           if(e instanceof UserAlreadyExistsException) {
              throw e;
           }
           return ResponseEntity.badRequest().body("Error creating user.");
        }

    }

    @GetMapping("/id={id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        Optional<UserResponse> foundUser = userService.findUserResponseById(id);
        return ResponseEntity.ok(foundUser.get());
    }

    @PutMapping("/id={id}")
    public ResponseEntity<?> updateUserById(@PathVariable Long id, @Valid @RequestBody CreateOrUpdateUserRequest request) {
        try {
            UserResponse updatedUser = userService.updateUserById(id, request);
            return ResponseEntity.ok(updatedUser);
        } catch (Exception e) {
            if (e instanceof UserNotFoundException || e instanceof UserValidationException) {
                throw e;
            }
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/id={id}")
    public ResponseEntity<?> deleteUserById(@PathVariable Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/fullinfo/id={id}")
    public ResponseEntity<Object> getFullUserInfoById(@PathVariable Long id) {
        Optional<UserResponse> foundUser = userService.findUserResponseById(id);
        return foundUser
                .<ResponseEntity<Object>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/fullinfo/name={name}")
    public ResponseEntity<Object> getFullUserInfoByName(@PathVariable String name) {
        Optional<UserResponse> foundUser = userService.findUserResponseByUsername(name);
        return foundUser.
                <ResponseEntity<Object>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}

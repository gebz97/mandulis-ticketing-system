package org.mandulis.mts.controller;

import org.mandulis.mts.service.UserService;
import org.mandulis.mts.dto.response.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/public/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/fullinfo/id={id}")
    public ResponseEntity<Object> getFullUserInfoById(@PathVariable Long id) {
        Optional<UserResponse> foundUser = userService.findUserResponseById(id);
        if (foundUser.isPresent()) {
            return ResponseEntity.ok(foundUser.get());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/fullinfo/name={name}")
    public ResponseEntity<Object> getFullUserInfoByName(@PathVariable String name) {
        Optional<UserResponse> foundUser = userService.findUserResponseByUsername(name);
        if (foundUser.isPresent()) {
            return ResponseEntity.ok(foundUser.get());
        }
        return ResponseEntity.notFound().build();
    }
}

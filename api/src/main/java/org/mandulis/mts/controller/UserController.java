package org.mandulis.mts.controller;

import jakarta.validation.Valid;
import org.mandulis.mts.dto.request.CreateOrUpdateUserRequest;
import org.mandulis.mts.service.UserService;
import org.mandulis.mts.dto.response.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/public/users")
public class UserController {
    private final UserService userService;

    // @Autowired isn't needed anymore https://docs.spring.io/spring-framework/reference/core/beans/dependencies/factory-collaborators.html
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserResponse> getAllUsers() {
        return userService.findAllUsers();
    }


    @PostMapping
    public ResponseEntity<?> createUser(@Valid @RequestBody CreateOrUpdateUserRequest request) {
        // if you really want to add this httpstatus, this function's output should be typed as ResponseEntity<UserResponse>
        // in general, don't use wildcare (?) unless you have no choice
        UserResponse createdUser = userService.saveUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }
    
    // in this case, I would have done it this way because, most of the time, in front end, you won't care about if it's a 200 or a 201
    // @PostMapping
    // public UserResponse createUser(@Valid @RequestBody CreateOrUpdateUserRequest request) {
        // return userService.saveUser(request);
    // }

    // if you want your URI to look like this '/api/v1/public/users/1', you only need "/{id}"
    // @GetMapping("{id}")
    @GetMapping("/id={id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        Optional<UserResponse> foundUser = userService.findUserResponseById(id);
        return ResponseEntity.ok(foundUser.get());
    }

    @PutMapping("/id={id}")
    public ResponseEntity<?> updateUserById(@PathVariable Long id, @Valid @RequestBody CreateOrUpdateUserRequest request) {
        UserResponse updatedUser = userService.updateUserById(id, request);
        return ResponseEntity.ok(updatedUser);
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

    @GetMapping("/search")
    public ResponseEntity<?> searchUsers(@RequestParam(required = false) String username,
                                        @RequestParam(required = false) String firstName,
                                        @RequestParam(required = false) String lastName,
                                        @RequestParam(required = false) String email) {

        return ResponseEntity.ok(userService.searchUsers(username, firstName, lastName, email));
    }
}

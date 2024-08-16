package org.mandulis.mts.user;

import jakarta.validation.Valid;
import org.mandulis.mts.rest.ApiResponse;
import org.mandulis.mts.rest.ErrorMessages;
import org.mandulis.mts.rest.ResponseHandler;
import org.mandulis.mts.rest.SuccessMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/public/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<UserResponse>>> getAllUsers() {
        List<UserResponse> users = userService.findAllUsers();
        return ResponseHandler.handleSuccess(users, HttpStatus.OK, SuccessMessages.QUERY_SUCCESSFUL);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<UserResponse>> createUser(@Valid @RequestBody UserRequest request) {
        return userService.saveUser(request)
                .map(response -> ResponseHandler.handleSuccess(
                        response, HttpStatus.CREATED, "User created successfully"
                ))
                .orElseGet(() -> ResponseHandler.handleError(
                        null, HttpStatus.BAD_REQUEST, ErrorMessages.INVALID_USER, List.of("User not found")
                ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> getUserById(@PathVariable Long id) {
        return userService.findUserResponseById(id)
                .map(user -> ResponseHandler.handleSuccess(user, HttpStatus.OK, SuccessMessages.QUERY_SUCCESSFUL))
                .orElseGet(() -> ResponseHandler.handleError(
                        null, HttpStatus.NOT_FOUND, "User not found", List.of("User not found")
                ));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> updateUserById(
            @PathVariable Long id, @Valid @RequestBody UserRequest request
    ) {
        return userService.updateUserById(id, request)
                .map(updatedUser -> ResponseHandler.handleSuccess(
                        updatedUser, HttpStatus.OK, "User updated successfully"
                ))
                .orElseGet(() -> ResponseHandler.handleError(
                        null, HttpStatus.NOT_FOUND, "User not found", List.of("User not found")
                ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteUserById(@PathVariable Long id) {
        userService.deleteUserById(id);
        return ResponseHandler.handleSuccess(null, HttpStatus.NO_CONTENT, "User deleted successfully");
    }

    @GetMapping("/fullinfo/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> getFullUserInfoById(@PathVariable Long id) {
        return userService.findUserResponseById(id)
                .map(user -> ResponseHandler.handleSuccess(user, HttpStatus.OK, SuccessMessages.QUERY_SUCCESSFUL))
                .orElseGet(() -> ResponseHandler.handleError(
                        null, HttpStatus.NOT_FOUND, "User not found", List.of("User not found")
                ));
    }

    @GetMapping("/fullinfo/name/{name}")
    public ResponseEntity<ApiResponse<UserResponse>> getFullUserInfoByName(@PathVariable String name) {
        return userService.findUserResponseByUsername(name)
                .map(user -> ResponseHandler.handleSuccess(user, HttpStatus.OK, SuccessMessages.QUERY_SUCCESSFUL))
                .orElseGet(() -> ResponseHandler.handleError(
                        null, HttpStatus.NOT_FOUND, "User not found", List.of("User not found")
                ));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<UserResponse>>> searchUsers(@RequestParam(required = false) String username,
                                                                       @RequestParam(required = false) String firstName,
                                                                       @RequestParam(required = false) String lastName,
                                                                       @RequestParam(required = false) String email) {
        List<UserResponse> users = userService.searchUsers(username, firstName, lastName, email);
        return ResponseHandler.handleSuccess(users, HttpStatus.OK, SuccessMessages.QUERY_SUCCESSFUL);
    }
}

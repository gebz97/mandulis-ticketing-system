package org.mandulis.mts.auth;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.mandulis.mts.rest.ResponseHandler;
import org.mandulis.mts.rest.SuccessMessages;
import org.mandulis.mts.user.UserService;
import org.mandulis.mts.user.RegistrationRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/public/register")
@RequiredArgsConstructor
public class RegistrationController {
    private final UserService userService;

    private final UserRegisterService userRegisterService;

    @PostMapping
    public ResponseEntity<Object> register(@Valid @RequestBody RegistrationRequest request){
        return ResponseHandler.response(SuccessMessages.REGISTRATION_SUCCESSFUL,
                HttpStatus.CREATED, userRegisterService.registerUser(request));
    }
}

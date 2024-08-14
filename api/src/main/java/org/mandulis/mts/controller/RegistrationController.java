package org.mandulis.mts.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.mandulis.mts.dto.response.ResponseHandler;
import org.mandulis.mts.dto.response.SuccessMessages;
import org.mandulis.mts.service.UserService;
import org.mandulis.mts.dto.request.RegistrationRequest;
import org.mandulis.mts.service.auth.UserRegisterService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


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

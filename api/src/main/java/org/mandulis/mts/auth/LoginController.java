package org.mandulis.mts.auth;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.mandulis.mts.user.LoginRequest;
import org.mandulis.mts.rest.ResponseHandler;
import org.mandulis.mts.rest.SuccessMessages;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/public/login")
@RequiredArgsConstructor
public class LoginController {
    private final UserLoginService userLoginService;

    @PostMapping
    public ResponseEntity<Object> login(@Valid @RequestBody LoginRequest request){
        return ResponseHandler.response(SuccessMessages.LOGIN_SUCCESSFUL, HttpStatus.OK, userLoginService.login(request));
    }
}

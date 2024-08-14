package org.mandulis.mts.controller;

import jakarta.validation.Valid;
import org.mandulis.mts.dto.LoginRequest;
import org.mandulis.mts.service.WebUserDetailsService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/public/login")
public class LoginController {
    private final PasswordEncoder passwordEncoder;
    private final WebUserDetailsService webUserDetailsService;

    public LoginController(PasswordEncoder passwordEncoder, WebUserDetailsService webUserDetailsService) {
        this.passwordEncoder = passwordEncoder;
        this.webUserDetailsService = webUserDetailsService;
    }

    @PostMapping
    public ResponseEntity<?> usernameAndPasswordLogin(@Valid @RequestBody LoginRequest loginRequest) {
        return null;
    }
}

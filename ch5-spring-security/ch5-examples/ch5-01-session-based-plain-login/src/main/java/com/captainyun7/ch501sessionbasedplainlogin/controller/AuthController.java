package com.captainyun7.ch501sessionbasedplainlogin.controller;

import com.captainyun7.ch501sessionbasedplainlogin.dto.LoginRequest;
import com.captainyun7.ch501sessionbasedplainlogin.dto.SignUpRequest;
import com.captainyun7.ch501sessionbasedplainlogin.dto.UserResponse;
import com.captainyun7.ch501sessionbasedplainlogin.service.AuthService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    
    private final AuthService authService;
    
    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody SignUpRequest signUpRequest) {
        UserResponse userResponse = authService.register(signUpRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
    }
    
    @PostMapping("/login")
    public ResponseEntity<UserResponse> login(@Valid @RequestBody LoginRequest loginRequest, HttpSession session) {
        UserResponse userResponse = authService.login(loginRequest, session);
        return ResponseEntity.ok(userResponse);
    }
    
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpSession session) {
        authService.logout(session);
        return ResponseEntity.ok().build();
    }
} 
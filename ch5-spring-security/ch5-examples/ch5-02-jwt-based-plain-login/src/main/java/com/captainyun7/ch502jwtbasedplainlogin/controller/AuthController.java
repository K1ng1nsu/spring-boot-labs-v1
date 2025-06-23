package com.captainyun7.ch502jwtbasedplainlogin.controller;

import com.captainyun7.ch502jwtbasedplainlogin.dto.LoginRequest;
import com.captainyun7.ch502jwtbasedplainlogin.dto.LoginResponse;
import com.captainyun7.ch502jwtbasedplainlogin.dto.SignUpRequest;
import com.captainyun7.ch502jwtbasedplainlogin.dto.UserResponse;
import com.captainyun7.ch502jwtbasedplainlogin.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

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
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        LoginResponse loginResponse = authService.login(loginRequest);
        return ResponseEntity.ok(loginResponse);
    }
    
    @PostMapping("/logout")
    public ResponseEntity<Map<String, String>> logout() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "로그아웃 되었습니다. 클라이언트에서 JWT 토큰을 삭제하세요.");
        return ResponseEntity.ok(response);
    }
} 
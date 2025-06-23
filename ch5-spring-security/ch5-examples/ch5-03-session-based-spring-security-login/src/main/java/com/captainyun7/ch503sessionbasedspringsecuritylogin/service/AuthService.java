package com.captainyun7.ch503sessionbasedspringsecuritylogin.service;

import com.captainyun7.ch503sessionbasedspringsecuritylogin.domain.User;
import com.captainyun7.ch503sessionbasedspringsecuritylogin.dto.LoginRequest;
import com.captainyun7.ch503sessionbasedspringsecuritylogin.dto.SignUpRequest;
import com.captainyun7.ch503sessionbasedspringsecuritylogin.dto.UserResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public UserResponse register(SignUpRequest signUpRequest) {
        // 사용자 이름 중복 검사
        if (userService.existsByUsername(signUpRequest.getUsername())) {
            throw new RuntimeException("이미 사용 중인 사용자 이름입니다");
        }
        
        // 이메일 중복 검사
        if (userService.existsByEmail(signUpRequest.getEmail())) {
            throw new RuntimeException("이미 사용 중인 이메일입니다");
        }
        
        // 비밀번호 암호화
        signUpRequest.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        
        return userService.createUser(signUpRequest);
    }

    public UserResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(),
                loginRequest.getPassword()
            )
        );
        
        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        return userService.getUserResponseByUsername(loginRequest.getUsername());
    }

    public void logout() {
        SecurityContextHolder.clearContext();
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        
        return userService.getUserByUsername(authentication.getName()).orElse(null);
    }
} 
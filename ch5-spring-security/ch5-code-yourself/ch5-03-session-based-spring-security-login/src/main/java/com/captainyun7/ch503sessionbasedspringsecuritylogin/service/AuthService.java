package com.captainyun7.ch503sessionbasedspringsecuritylogin.service;

import com.captainyun7.ch503sessionbasedspringsecuritylogin.domain.User;
import com.captainyun7.ch503sessionbasedspringsecuritylogin.dto.LoginRequest;
import com.captainyun7.ch503sessionbasedspringsecuritylogin.dto.SignUpRequest;
import com.captainyun7.ch503sessionbasedspringsecuritylogin.dto.UserResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    public UserResponse register(SignUpRequest signUpRequest) {
        // 사용자 이름 중복 검사
        if (userService.existsByUsername(signUpRequest.getUsername())) {
            throw new RuntimeException("이미 사용 중인 사용자 이름입니다");
        }

        // 이메일 중복 검사
        if (userService.existsByEmail(signUpRequest.getEmail())) {
            throw new RuntimeException("이미 사용 중인 이메일입니다");
        }

        signUpRequest.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));

        return userService.createUser(signUpRequest);
    }

    public UserResponse login(LoginRequest loginRequest) {
        // TODO: [2] AuthenticationManager를 사용하여 사용자를 인증합니다.
        // TODO: [3] 인증에 성공하면 SecurityContext에 인증 정보를 저장합니다.
        // TODO: [4] 인증된 사용자 정보를 반환합니다.

        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authenticate);

        return userService.getUserResponseByUsername(loginRequest.getUsername());
    }

    public void logout(HttpServletRequest request, HttpServletResponse response) {
        // TODO: [5] SecurityContext에서 인증 정보를 제거하여 로그아웃 처리합니다.
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }

        return userService.getUserByUsername(authentication.getName()).orElse(null);
    }
} 
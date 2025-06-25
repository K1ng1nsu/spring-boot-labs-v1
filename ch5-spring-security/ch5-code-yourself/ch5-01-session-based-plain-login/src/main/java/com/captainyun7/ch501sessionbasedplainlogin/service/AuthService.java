package com.captainyun7.ch501sessionbasedplainlogin.service;

import com.captainyun7.ch501sessionbasedplainlogin.dto.LoginRequest;
import com.captainyun7.ch501sessionbasedplainlogin.dto.SignUpRequest;
import com.captainyun7.ch501sessionbasedplainlogin.dto.UserResponse;
import com.captainyun7.ch501sessionbasedplainlogin.domain.User;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private static final String USER_SESSION_KEY = "CURRENT_USER";

    public UserResponse register(SignUpRequest signUpRequest) {
        // TODO
        if (userService.existsByUsername(signUpRequest.getUsername()) || userService.existsByEmail(signUpRequest.getEmail())) {
            throw new RuntimeException("username or email already exists");
        }

        return userService.createUser(signUpRequest);
    }

    public UserResponse login(LoginRequest loginRequest, HttpSession session) {
        User user = userService.getUserByUsername(loginRequest.getUsername()).orElseThrow(() -> new RuntimeException("username not found"));

        if (!user.getPassword().equals(loginRequest.getPassword())) {
            throw new RuntimeException("password not match");
        }

        session.setAttribute(USER_SESSION_KEY, user);

        return UserResponse.fromEntity(user);
    }

    public void logout(HttpSession session) {
        session.removeAttribute(USER_SESSION_KEY);
        session.invalidate();

    }

    public User getCurrentUser(HttpSession session) {
        return (User) session.getAttribute(USER_SESSION_KEY);
    }
} 
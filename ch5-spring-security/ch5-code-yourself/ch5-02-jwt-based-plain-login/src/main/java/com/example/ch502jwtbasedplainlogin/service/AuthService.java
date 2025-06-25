package com.example.ch502jwtbasedplainlogin.service;

import com.example.ch502jwtbasedplainlogin.config.JwtUtil;
import com.example.ch502jwtbasedplainlogin.dto.LoginRequest;
import com.example.ch502jwtbasedplainlogin.dto.LoginResponse;
import com.example.ch502jwtbasedplainlogin.dto.SignUpRequest;
import com.example.ch502jwtbasedplainlogin.dto.UserResponse;
import com.example.ch502jwtbasedplainlogin.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
//    private static final String USER_SESSION_KEY = "CURRENT_USER";

    private final JwtUtil jwtUtil;

    public UserResponse register(SignUpRequest signUpRequest) {
        // TODO
        if (userService.existsByUsername(signUpRequest.getUsername()) || userService.existsByEmail(signUpRequest.getEmail())) {
            throw new RuntimeException("username or email already exists");
        }

        return userService.createUser(signUpRequest);
    }

    public LoginResponse login(LoginRequest loginRequest) {
        User user = userService.getUserByUsername(loginRequest.getUsername()).orElseThrow(() -> new RuntimeException("username not found"));

        if (!user.getPassword().equals(loginRequest.getPassword())) {
            throw new RuntimeException("password not match");
        }

//        session.setAttribute(USER_SESSION_KEY, user);

        String token =  jwtUtil.generateToken(user);

        LoginResponse loginResponse = LoginResponse.fromEntity(user);
        loginResponse.setToken(token);


        return loginResponse;
    }

    public void logout() {
//        session.removeAttribute(USER_SESSION_KEY);
//        session.invalidate();

    }

//    public User getCurrentUser(HttpSession session) {
//        return (User) session.getAttribute(USER_SESSION_KEY);
//    }
} 
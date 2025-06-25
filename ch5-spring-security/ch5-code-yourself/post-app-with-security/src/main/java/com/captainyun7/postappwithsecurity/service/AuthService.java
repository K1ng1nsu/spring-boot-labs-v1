package com.captainyun7.postappwithsecurity.service;

import com.captainyun7.postappwithsecurity.dto.JwtResponse;
import com.captainyun7.postappwithsecurity.dto.LoginRequest;
import com.captainyun7.postappwithsecurity.dto.SignupRequest;
import com.captainyun7.postappwithsecurity.dto.UserResponse;
import com.captainyun7.postappwithsecurity.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;


    public UserResponse createUser(SignupRequest signupRequest) {
        if (userService.existUserByUsername(signupRequest.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        return userService.createUser(signupRequest);
    }

    public JwtResponse login(LoginRequest loginRequest) {

        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authenticate);

        UserDetails principal = (UserDetails) authenticate.getPrincipal();

        String jwt = jwtUtil.generateToken(principal);

        return new JwtResponse(jwt);
    }
}

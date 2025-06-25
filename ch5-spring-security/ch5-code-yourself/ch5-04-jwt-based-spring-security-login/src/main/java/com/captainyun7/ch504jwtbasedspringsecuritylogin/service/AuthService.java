package com.captainyun7.ch504jwtbasedspringsecuritylogin.service;

import com.captainyun7.ch504jwtbasedspringsecuritylogin.domain.RefreshToken;
import com.captainyun7.ch504jwtbasedspringsecuritylogin.domain.User;
import com.captainyun7.ch504jwtbasedspringsecuritylogin.dto.JwtResponse;
import com.captainyun7.ch504jwtbasedspringsecuritylogin.dto.LoginRequest;
import com.captainyun7.ch504jwtbasedspringsecuritylogin.dto.SignUpRequest;
import com.captainyun7.ch504jwtbasedspringsecuritylogin.dto.UserResponse;
import com.captainyun7.ch504jwtbasedspringsecuritylogin.util.JwtUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final RefreshTokenService refreshTokenService;

    @Transactional
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

    @Transactional
    public JwtResponse login(LoginRequest loginRequest) {
        // TODO: 로그인 로직을 구현합니다.
        // 1. AuthenticationManager를 사용하여 사용자 인증을 수행합니다.
        //    - UsernamePasswordAuthenticationToken을 생성하여 사용자의 아이디와 비밀번호를 담습니다.
        // 2. 인증에 성공하면, SecurityContextHolder에 인증 정보를 저장합니다.
        // 3. 인증된 사용자 정보를 기반으로 Access Token(JwtUtil.generateToken)과 Refresh Token(RefreshTokenService.createRefreshToken)을 생성합니다.
        // 4. 생성된 토큰들을 JwtResponse DTO에 담아 반환합니다.

        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authenticate);

        UserDetails principal = (UserDetails) authenticate.getPrincipal();

        String accessToken = jwtUtil.generateToken(principal);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(principal.getUsername());


        return new JwtResponse(accessToken, refreshToken.getToken());
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }

        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();
            return userService.getUserByUsername(username).orElse(null);
        }

        return null;
    }

    public String refreshAccessToken(String refreshToken) {
        // TODO: Refresh Token을 사용하여 새로운 Access Token을 발급하는 로직을 구현합니다.
        // 1. RefreshTokenService를 사용하여 데이터베이스에서 Refresh Token을 찾습니다.
        //    - `findByToken()`
        // 2. 토큰이 유효한지 검증합니다.
        //    - `verifyExpiration()`
        // 3. 토큰과 연관된 사용자 정보를 가져옵니다.
        // 4. 새로운 Access Token을 생성합니다(JwtUtil.generateToken).
        // 5. 토큰을 찾을 수 없거나 유효하지 않은 경우 예외를 발생시킵니다.

        String accessToken = refreshTokenService.findByToken(refreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> jwtUtil.generateToken(
                        new org.springframework.security.core.userdetails.User(
                                user.getUsername(),
                                user.getPassword(),
                                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole())))
                )).orElseThrow(EntityNotFoundException::new);


        return accessToken;
    }

    @Transactional
    public void logout() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        refreshTokenService.deleteByUsername(username);
    }
} 
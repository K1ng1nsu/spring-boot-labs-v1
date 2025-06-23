package com.captainyun7.ch502jwtbasedplainlogin.service;

import com.captainyun7.ch502jwtbasedplainlogin.config.JwtUtil;
import com.captainyun7.ch502jwtbasedplainlogin.domain.User;
import com.captainyun7.ch502jwtbasedplainlogin.dto.LoginRequest;
import com.captainyun7.ch502jwtbasedplainlogin.dto.LoginResponse;
import com.captainyun7.ch502jwtbasedplainlogin.dto.SignUpRequest;
import com.captainyun7.ch502jwtbasedplainlogin.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    
    private final UserService userService;
    private final JwtUtil jwtUtil;

    public UserResponse register(SignUpRequest signUpRequest) {
        // 사용자 이름 중복 검사
        if (userService.existsByUsername(signUpRequest.getUsername())) {
            throw new RuntimeException("이미 사용 중인 사용자 이름입니다");
        }
        
        // 이메일 중복 검사
        if (userService.existsByEmail(signUpRequest.getEmail())) {
            throw new RuntimeException("이미 사용 중인 이메일입니다");
        }
        
        return userService.createUser(signUpRequest);
    }

    public LoginResponse login(LoginRequest loginRequest) {
        User user = userService.getUserByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다"));
        
        // 비밀번호 검증 (실제 환경에서는 암호화된 비밀번호 비교 필요)
        if (!user.getPassword().equals(loginRequest.getPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다");
        }
        
        // JWT 토큰 생성
        String token = jwtUtil.generateToken(user);
        
        return LoginResponse.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .role(user.getRole())
                .token(token)
                .build();
    }

    public User getUserFromToken(String token) {
        if (token != null && jwtUtil.validateToken(token)) {
            String email = jwtUtil.getEmailFromToken(token);
            return userService.getUserByEmail(email)
                    .orElseThrow(() -> new RuntimeException("토큰에서 사용자를 찾을 수 없습니다"));
        }
        throw new RuntimeException("유효하지 않은 토큰입니다");
    }
} 
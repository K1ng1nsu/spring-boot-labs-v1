package com.captainyun7.ch503sessionbasedspringsecuritylogin.service;

import com.captainyun7.ch503sessionbasedspringsecuritylogin.domain.User;
import com.captainyun7.ch503sessionbasedspringsecuritylogin.dto.SignUpRequest;
import com.captainyun7.ch503sessionbasedspringsecuritylogin.dto.UserResponse;
import com.captainyun7.ch503sessionbasedspringsecuritylogin.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    
    private final UserRepository userRepository;

    public UserResponse createUser(SignUpRequest signUpRequest) {
        User user = User.builder()
                .username(signUpRequest.getUsername())
                .password(signUpRequest.getPassword()) // 암호화된 비밀번호가 전달됨
                .email(signUpRequest.getEmail())
                .role("USER") // 기본 권한은 USER
                .build();
        
        User savedUser = userRepository.save(user);
        return UserResponse.fromEntity(savedUser);
    }

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserResponse::fromEntity)
                .collect(Collectors.toList());
    }

    public UserResponse getUserById(Long id) {
        return userRepository.findById(id)
                .map(UserResponse::fromEntity)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다: " + id));
    }

    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    
    public UserResponse getUserResponseByUsername(String username) {
        return getUserByUsername(username)
                .map(UserResponse::fromEntity)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다: " + username));
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
} 
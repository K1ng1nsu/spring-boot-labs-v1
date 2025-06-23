package com.captainyun7.ch502jwtbasedplainlogin.service;

import com.captainyun7.ch502jwtbasedplainlogin.domain.User;
import com.captainyun7.ch502jwtbasedplainlogin.dto.SignUpRequest;
import com.captainyun7.ch502jwtbasedplainlogin.dto.UserResponse;
import com.captainyun7.ch502jwtbasedplainlogin.repository.UserRepository;
import lombok.RequiredArgsConstructor;
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
                .password(signUpRequest.getPassword()) // 실제 환경에서는 암호화 필요
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
    
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
} 
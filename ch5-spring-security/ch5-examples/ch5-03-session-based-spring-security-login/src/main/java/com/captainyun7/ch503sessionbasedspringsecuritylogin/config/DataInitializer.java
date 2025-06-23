package com.captainyun7.ch503sessionbasedspringsecuritylogin.config;

import com.captainyun7.ch503sessionbasedspringsecuritylogin.domain.User;
import com.captainyun7.ch503sessionbasedspringsecuritylogin.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        // 테스트용 관리자 계정 생성
        User admin = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin123"))
                .email("admin@example.com")
                .role("ADMIN")
                .build();
        userRepository.save(admin);

        // 테스트용 일반 사용자 계정 생성
        User user1 = User.builder()
                .username("user1")
                .password(passwordEncoder.encode("user123"))
                .email("user1@example.com")
                .role("USER")
                .build();
        userRepository.save(user1);
        
        User user2 = User.builder()
                .username("user2")
                .password(passwordEncoder.encode("user123"))
                .email("user2@example.com")
                .role("USER")
                .build();
        userRepository.save(user2);
        
        User user3 = User.builder()
                .username("user3")
                .password(passwordEncoder.encode("user123"))
                .email("user3@example.com")
                .role("USER")
                .build();
        userRepository.save(user3);
    }
} 
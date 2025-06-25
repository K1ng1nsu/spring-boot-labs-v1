package com.captainyun7.postappwithsecurity.service;

import com.captainyun7.postappwithsecurity.domain.User;
import com.captainyun7.postappwithsecurity.dto.LoginRequest;
import com.captainyun7.postappwithsecurity.dto.SignupRequest;
import com.captainyun7.postappwithsecurity.dto.UserResponse;
import com.captainyun7.postappwithsecurity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
    }

    public boolean existUserByUsername(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    public UserResponse createUser(SignupRequest signUpRequest) {
        User user = new User();
        user.setUsername(signUpRequest.getUsername());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        user.setRole("USER");

        User save = userRepository.save(user);
        return UserResponse.from(save);
    }


}

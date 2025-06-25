package com.example.ch502jwtbasedplainlogin.dto;

import com.example.ch502jwtbasedplainlogin.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    private Long userId;
    private String email;
    private String role;
    private String token;


    public static LoginResponse fromEntity(User user) {
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setUserId(user.getId());
        loginResponse.setEmail(user.getEmail());
        loginResponse.setRole(user.getRole());
        return loginResponse;
    }
}

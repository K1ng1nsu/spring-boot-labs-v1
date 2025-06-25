package com.captainyun7.postappwithsecurity.dto;

import com.captainyun7.postappwithsecurity.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private String username;
    private String role;

    public static UserResponse from(User save) {
        UserResponse userResponse = new UserResponse();
        userResponse.setUsername(save.getUsername());
        userResponse.setRole(save.getRole());
        return userResponse;
    }
}

package com.captainyun7.ch503sessionbasedspringsecuritylogin.controller;

import com.captainyun7.ch503sessionbasedspringsecuritylogin.dto.UserResponse;
import com.captainyun7.ch503sessionbasedspringsecuritylogin.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    
    private final UserService userService;
    
    @GetMapping("/users")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/dashboard")
    public ResponseEntity<Map<String, Object>> getDashboard() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "관리자 대시보드에 접근했습니다");
        response.put("status", "success");
        return ResponseEntity.ok(response);
    }
} 
package com.captainyun7.ch503sessionbasedspringsecuritylogin.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/debug")
public class DebugController {

    @GetMapping("/auth-status")
    public ResponseEntity<?> getAuthStatus(HttpServletRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Map<String, Object> debugInfo = new HashMap<>();
        debugInfo.put("authenticated", auth != null && auth.isAuthenticated());
        debugInfo.put("authorities", auth != null ? auth.getAuthorities() : null);
        debugInfo.put("principal", auth != null ? auth.getName() : null);
        debugInfo.put("sessionId", request.getSession().getId());

        return ResponseEntity.ok(debugInfo);
    }
}
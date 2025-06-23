package com.captainyun7.ch502jwtbasedplainlogin.interceptor;

import com.captainyun7.ch502jwtbasedplainlogin.domain.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class AuthorizationInterceptor implements HandlerInterceptor {
    
    private static final String USER_SESSION_KEY = "CURRENT_USER";
    private final ObjectMapper objectMapper;
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String path = request.getRequestURI();
        
        // 관리자 API에 대한 권한 확인
        if (path.startsWith("/api/admin")) {
            HttpSession session = request.getSession(false);
            
            if (session == null) {
                sendUnauthorizedResponse(response, "인증이 필요합니다");
                return false;
            }
            
            User user = (User) session.getAttribute(USER_SESSION_KEY);
            
            if (user == null) {
                sendUnauthorizedResponse(response, "인증이 필요합니다");
                return false;
            }
            
            // 관리자 권한 확인
            if (!"ADMIN".equals(user.getRole())) {
                sendForbiddenResponse(response, "관리자 권한이 필요합니다");
                return false;
            }
        }
        
        return true;
    }
    
    private void sendUnauthorizedResponse(HttpServletResponse response, String message) throws Exception {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("message", message);
        errorResponse.put("status", "error");
        
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }
    
    private void sendForbiddenResponse(HttpServletResponse response, String message) throws Exception {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");
        
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("message", message);
        errorResponse.put("status", "error");
        
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }
} 
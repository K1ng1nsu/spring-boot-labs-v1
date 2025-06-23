package com.captainyun7.ch502jwtbasedplainlogin.filter;

import com.captainyun7.ch502jwtbasedplainlogin.config.JwtUtil;
import com.captainyun7.ch502jwtbasedplainlogin.domain.User;
import com.captainyun7.ch502jwtbasedplainlogin.service.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Order(1)
@RequiredArgsConstructor
public class AuthenticationFilter implements Filter {
    
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";
    private static final List<String> PUBLIC_PATHS = Arrays.asList(
            "/swagger-ui",
            "/v3/api-docs",
            "/swagger-resources/**",
            "/api/auth/login",
            "/api/auth/register",
            "/h2-console"
    );
    
    private final ObjectMapper objectMapper;
    private final JwtUtil jwtUtil;
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        
        String path = httpRequest.getRequestURI();
        
        // 공개 경로는 인증 없이 접근 가능
        if (isPublicPath(path)) {
            chain.doFilter(request, response);
            return;
        }
        
        String jwt = getJwtFromRequest(httpRequest);
        
        // JWT가 없거나 유효하지 않으면 인증 실패
        if (jwt == null || !jwtUtil.validateToken(jwt)) {
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            httpResponse.setContentType("application/json");
            
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "인증이 필요합니다");
            errorResponse.put("status", "error");
            
            httpResponse.getWriter().write(objectMapper.writeValueAsString(errorResponse));
            return;
        }
        
        // 인증된 사용자는 요청 진행
        chain.doFilter(request, response);
    }
    
    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }
    
    private boolean isPublicPath(String path) {
        return PUBLIC_PATHS.stream().anyMatch(path::startsWith);
    }
} 
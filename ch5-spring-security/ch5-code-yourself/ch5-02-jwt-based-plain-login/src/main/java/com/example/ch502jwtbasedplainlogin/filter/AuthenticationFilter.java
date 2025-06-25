package com.example.ch502jwtbasedplainlogin.filter;

import com.example.ch502jwtbasedplainlogin.config.JwtUtil;
import com.example.ch502jwtbasedplainlogin.domain.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Order(1)
@RequiredArgsConstructor
public class AuthenticationFilter implements Filter {

    private final JwtUtil  jwtUtil;

    private static final String USER_SESSION_KEY = "CURRENT_USER";
    private static final List<String> PUBLIC_PATHS = Arrays.asList(
            "/swagger-ui",
            "/v3/api-docs",
            "/swagger-resources/**",
            "/api/auth/login",
            "/api/auth/register",
            "/h2-console"
    );

    private final ObjectMapper objectMapper;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;

        if (isPublicPath(req.getRequestURI())) {
            chain.doFilter(request, response);
        } else {
//            HttpSession session = req.getSession(false);
//            if (session == null || session.getAttribute(USER_SESSION_KEY) == null) {
//                HttpServletResponse resp = (HttpServletResponse) response;
//                resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//                resp.setContentType("application/json");
//                resp.setCharacterEncoding("UTF-8");
//
//                Map<String, String> errorResponse = new HashMap<>();
//
//                errorResponse.put("message", "You are not logged in");
//                errorResponse.put("status", "401");
//                resp.getWriter().println(objectMapper.writeValueAsString(errorResponse));
//
//            } else {
//                chain.doFilter(request, response);
//            }

            String authorization = req.getHeader("Authorization");
            if (authorization == null || !authorization.startsWith("Bearer ")|| !jwtUtil.validateToken(authorization.substring(7))) {

                HttpServletResponse resp = (HttpServletResponse) response;
                resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");

                Map<String, String> errorResponse = new HashMap<>();

                errorResponse.put("message", "You are not logged in or token is expired");
                errorResponse.put("status", "401");
                resp.getWriter().println(objectMapper.writeValueAsString(errorResponse));

            }else{
                chain.doFilter(request, response);
            }

        }


    }

    private boolean isPublicPath(String path) {
        return PUBLIC_PATHS.stream().anyMatch(path::startsWith);
    }
} 
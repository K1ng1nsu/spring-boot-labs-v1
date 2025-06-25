package com.example.ch502jwtbasedplainlogin.interceptor;

import com.example.ch502jwtbasedplainlogin.config.JwtUtil;
import com.example.ch502jwtbasedplainlogin.domain.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class AuthorizationInterceptor implements HandlerInterceptor {

    private final JwtUtil jwtUtil;
    private static final String USER_SESSION_KEY = "CURRENT_USER";
    private final ObjectMapper objectMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String path = request.getRequestURI();

        if (path.startsWith("/api/admin")) {
//            HttpSession session = request.getSession(false);
//
//            if (session == null) {
//                sendUnauthorizedResponse(response, "You are not logged in");
//                return false;
//            }
//            User user = (User) session.getAttribute(USER_SESSION_KEY);
//
//            if (user == null) {
//                sendUnauthorizedResponse(response, "You are not logged in");
//            }
//
//            String role = user.getRole();
//
//            if (!role.equals("ADMIN")) {
//                sendForbiddenResponse(response, "You are not authorized to perform this action");
//                return false;
//            }

            String authorization = request.getHeader("Authorization");

            if ((authorization != null && authorization.startsWith("Bearer ")) && jwtUtil.validateToken(authorization.substring(7))) {
                String token = authorization.substring(7);
                String roleFromToken = jwtUtil.getRoleFromToken(token);
                if (!roleFromToken.equals("ADMIN")) {
                    sendForbiddenResponse(response, "You are not authorized to perform this action");
                    return false;
                }

            } else {
                sendUnauthorizedResponse(response, "You are not logged in");
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
        errorResponse.put("status", "401");

        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }

    private void sendForbiddenResponse(HttpServletResponse response, String message) throws Exception {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");

        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("message", message);
        errorResponse.put("status", "403");

        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }
} 
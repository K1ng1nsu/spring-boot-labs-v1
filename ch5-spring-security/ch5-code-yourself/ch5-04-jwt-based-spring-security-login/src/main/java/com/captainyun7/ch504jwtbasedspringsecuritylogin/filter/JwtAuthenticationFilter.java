package com.captainyun7.ch504jwtbasedspringsecuritylogin.filter;

import com.captainyun7.ch504jwtbasedspringsecuritylogin.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, UserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // TODO: JWT 인증 필터 로직을 구현합니다.
        // 1. 요청 헤더(Authorization)에서 'Bearer ' 접두사를 제외한 JWT를 추출합니다.
        // 2. JWT가 존재하고 유효한 경우, 토큰에서 사용자 이름을 추출합니다.
        // 3. SecurityContext에 인증 정보가 없는 경우, UserDetailsService에서 사용자 정보를 로드합니다.
        // 4. 토큰이 유효한지 검증합니다(JwtUtil.validateToken).
        // 5. 유효한 토큰인 경우, UsernamePasswordAuthenticationToken을 생성하고 사용자의 상세 정보(WebAuthenticationDetailsSource)를 설정합니다.
        // 6. SecurityContextHolder에 인증 정보를 설정합니다.

        String authorization = request.getHeader("Authorization");

        if (authorization == null || !authorization.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authorization.substring(7);
        String usernameFromToken;
        try {
            usernameFromToken = jwtUtil.getUsernameFromToken(token);
        } catch (Exception e) {
            //
            System.out.println("####################");
            System.out.println("####################");
            System.out.println(e.getMessage());
            System.out.println("####################");
            System.out.println("####################");
            filterChain.doFilter(request, response);
            return;
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails;
        if (authentication == null) {
            userDetails = userDetailsService.loadUserByUsername(usernameFromToken);

            if (jwtUtil.validateToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }


//        Boolean isValid = jwtUtil.validateToken(token, userDetails);


        filterChain.doFilter(request, response);
    }
} 
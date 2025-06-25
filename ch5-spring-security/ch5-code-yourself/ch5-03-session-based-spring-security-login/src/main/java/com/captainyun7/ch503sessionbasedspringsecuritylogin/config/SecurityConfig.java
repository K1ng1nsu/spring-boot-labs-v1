package com.captainyun7.ch503sessionbasedspringsecuritylogin.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.ArrayList;
import java.util.List;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final String[] PUBLIC_PATH = {"/api/auth/**", "/swagger-ui/**","*/swagger-ui/**"};
    private final String[] ADMIN_PATH = {"/api/admin/**"};

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                // TODO: [1] URL별 접근 권한을 설정합니다.
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests.requestMatchers(PUBLIC_PATH).permitAll()
                                .requestMatchers(ADMIN_PATH).hasRole("ADMIN")
                                .anyRequest().authenticated()
                )
                .formLogin(form -> form.disable())
                .httpBasic(basic -> basic.disable())
                .sessionManagement(
                        sessionManagement ->
                                sessionManagement
                                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                                        .maximumSessions(1)
                                        .expiredUrl("/api/auth/login")
                ).securityContext(
                        securityContext ->
                                securityContext.requireExplicitSave(false))
                .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable()))
        // TODO: [2] 세션 관리 설정을 합니다.
        ;

        // H2 콘솔 사용을 위한 설정
//        http.headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable()));

        return http.build();
    }

    // TODO: [1] PasswordEncoder를 Bean으로 등록합니다.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
} 
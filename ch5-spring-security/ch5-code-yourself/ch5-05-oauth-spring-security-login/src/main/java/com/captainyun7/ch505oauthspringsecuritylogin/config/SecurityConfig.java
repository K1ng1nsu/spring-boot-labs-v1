package com.captainyun7.ch505oauthspringsecuritylogin.config;

import com.captainyun7.ch505oauthspringsecuritylogin.filter.JwtAuthenticationFilter;
import com.captainyun7.ch505oauthspringsecuritylogin.handler.OAuth2AuthenticationFailureHandler;
import com.captainyun7.ch505oauthspringsecuritylogin.handler.OAuth2AuthenticationSuccessHandler;
import com.captainyun7.ch505oauthspringsecuritylogin.service.OAuth2UserServiceImpl;
import lombok.RequiredArgsConstructor;
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
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final OAuth2UserServiceImpl oAuth2UserService;
    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
    private final OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/login", "/api/auth/**", "/h2-console/**", "/oauth2/**").permitAll()
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                // TODO : OIDC 설정
                .oauth2Login(oauth -> oauth
                        .userInfoEndpoint(userinfo -> userinfo
                                .oidcUserService(oAuth2UserService)) // 3. 성공적으로 받아오면 받은 토큰으로부터 얘가 가공 -> 성공, 실패
                        .successHandler(oAuth2AuthenticationSuccessHandler) // 4. 성공시
                        .failureHandler(oAuth2AuthenticationFailureHandler) // 5. 실패시
                        .authorizationEndpoint(auth -> auth.baseUri("/oauth2/authorize")) //1. 시작점 /oauth2/authorize / {registrationId} yml에
                        .redirectionEndpoint(redirection -> redirection.baseUri("/login/oauth2/code/*")) //2. 유저가 로그인하면 프로바이더가 리다이렉션  OAuth2LoginAuthenticationFilter 가 처리함 코드 가지고 토큰 받아옴
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        // H2 콘솔 사용을 위한 설정
        http.headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable()));

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
} 
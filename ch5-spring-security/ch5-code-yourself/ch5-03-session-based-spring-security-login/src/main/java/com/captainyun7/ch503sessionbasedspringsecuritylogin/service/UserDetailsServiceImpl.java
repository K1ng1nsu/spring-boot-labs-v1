package com.captainyun7.ch503sessionbasedspringsecuritylogin.service;

import com.captainyun7.ch503sessionbasedspringsecuritylogin.domain.User;
import com.captainyun7.ch503sessionbasedspringsecuritylogin.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // TODO: [6] 사용자 이름(username)을 사용하여 데이터베이스에서 사용자 정보를 조회합니다.
        // TODO: [7] 조회된 사용자 정보가 없으면 UsernameNotFoundException을 발생시킵니다.
        // TODO: [8] 조회된 사용자 정보를 기반으로 UserDetails 객체를 생성하여 반환합니다.
        //      - UserDetails는 Spring Security에서 사용자의 정보를 나타내는 인터페이스입니다.
        //      - org.springframework.security.core.userdetails.User 클래스를 사용하여 UserDetails 객체를 생성할 수 있습니다.

        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("CAN NOT FOUND USER :" + username));


        return org.springframework.security.core.userdetails.User
                .builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole())
//                .authorities(Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole())))
                .build();
    }
} 
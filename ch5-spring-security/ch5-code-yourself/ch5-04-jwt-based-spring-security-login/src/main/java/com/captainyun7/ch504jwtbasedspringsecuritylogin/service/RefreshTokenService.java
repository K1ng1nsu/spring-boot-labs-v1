package com.captainyun7.ch504jwtbasedspringsecuritylogin.service;

import com.captainyun7.ch504jwtbasedspringsecuritylogin.domain.RefreshToken;
import com.captainyun7.ch504jwtbasedspringsecuritylogin.domain.User;
import com.captainyun7.ch504jwtbasedspringsecuritylogin.repository.RefreshTokenRepository;
import com.captainyun7.ch504jwtbasedspringsecuritylogin.repository.UserRepository;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {

    @Value("${jwt.refresh-expiration}")
    private Long refreshTokenDurationMs;

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository, UserRepository userRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.userRepository = userRepository;
    }

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    @Transactional
    public RefreshToken createRefreshToken(String username) {
        // TODO: 사용자 이름(username)을 기반으로 새로운 Refresh Token을 생성하고 저장하는 로직을 구현합니다.
        // 1. UserRepository를 사용하여 사용자 정보를 조회합니다. 사용자가 없으면 예외를 발생시킵니다.
        // 2. 해당 사용자의 기존 Refresh Token이 있다면 삭제합니다(선택 사항이지만 권장).
        // 3. 새로운 RefreshToken 객체를 생성합니다.
        //    - 사용자 정보(User) 설정
        //    - 만료 시간 설정 (현재 시간 + `refreshTokenDurationMs`)
        //    - UUID를 사용하여 랜덤 토큰 문자열 생성
        // 4. 생성된 Refresh Token을 RefreshTokenRepository를 통해 데이터베이스에 저장하고 반환합니다.

        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(user);
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs * 1000));


        return refreshTokenRepository.save(refreshToken);
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        // TODO: 토큰의 만료 시간을 검증하는 로직을 구현합니다.
        // 1. 토큰의 만료 시간(`getExpiryDate()`)이 현재 시간(`Instant.now()`)보다 이전인지 확인합니다.
        // 2. 만료되었다면, 데이터베이스에서 해당 토큰을 삭제하고 예외를 발생시킵니다.
        // 3. 만료되지 않았다면, 원래의 토큰을 그대로 반환합니다.

        Instant expiryDate = token.getExpiryDate();
        if (expiryDate.isBefore(Instant.now())) {
            refreshTokenRepository.delete(token);
            throw new RuntimeException("Refresh Token has expired");
        }


        return token;
    }

    @Transactional
    public int deleteByUsername(String username) {
        return userRepository.findByUsername(username).map(user -> {
            refreshTokenRepository.deleteByUserId(user.getId());
            return 1;
        }).orElse(0);
    }
} 
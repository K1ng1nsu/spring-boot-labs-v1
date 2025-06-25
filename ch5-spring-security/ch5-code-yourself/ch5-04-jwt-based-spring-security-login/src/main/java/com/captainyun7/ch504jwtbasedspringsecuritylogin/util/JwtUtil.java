package com.captainyun7.ch504jwtbasedspringsecuritylogin.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil {

    // IMPORTANT: This key should be stored securely and not hardcoded.
    // e.g., in application.yml and injected via @Value
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    @Value("${jwt.refresh-expiration}")
    private Long refreshExpiration;

    private Key getSigningKey() {
        byte[] keyBytes = secret.getBytes();
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        // TODO: 토큰에서 모든 클레임을 추출합니다.
        // Jwts.parserBuilder()를 사용하여 파서를 만들고, 서명 키를 설정한 후 토큰을 파싱하여 본문(claims)을 가져옵니다.

        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token) {
        final Date expirationDate = getExpirationDateFromToken(token);
        return expirationDate.before(new Date());
    }

    public String generateToken(UserDetails userDetails) {

        return doGenerateToken(userDetails.getUsername(), expiration);
    }

    public String generateRefreshToken(UserDetails userDetails) {
        return doGenerateToken(userDetails.getUsername(), refreshExpiration);
    }

    private String doGenerateToken(String username, long expirationTime) {
        // TODO: 사용자 이름과 만료 시간을 기반으로 JWT를 생성합니다.
        // Claims를 설정하고(setSubject), Jwts.builder()를 사용하여 토큰을 빌드합니다.
        // 발급 시간(setIssuedAt), 만료 시간(setExpiration), 서명(signWith)을 설정해야 합니다.
        Claims claims = Jwts.claims();
        claims.setSubject(username);


        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime * 1000))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        // TODO: 토큰의 유효성을 검사합니다.
        // 토큰에서 추출한 사용자 이름이 userDetails의 사용자 이름과 일치하는지, 그리고 토큰이 만료되지 않았는지 확인합니다.
        return getUsernameFromToken(token).equals(userDetails.getUsername()) && !isTokenExpired(token);
    }
} 
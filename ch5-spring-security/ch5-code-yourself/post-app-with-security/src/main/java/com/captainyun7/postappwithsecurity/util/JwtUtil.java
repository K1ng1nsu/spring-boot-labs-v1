package com.captainyun7.postappwithsecurity.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class JwtUtil {
    private final String secret = "qiweoopwqkeopiwqpoeiqwepowqipeoqiwpeoiowqiepoiwqpeiqwpoeqweqweqwewq";
    private final long expiration = 3600;


    private Key getKey() {
        byte[] decode = Base64.getDecoder().decode(secret);
        return Keys.hmacShaKeyFor(decode);
    }

    public String generateToken(UserDetails userDetails) {
        Claims claims = Jwts.claims().setSubject(userDetails.getUsername());

        return createToken(claims);
    }


    public String getUsernameByToken(String token) {
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
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }


    public String createToken(Claims claims) {
        return Jwts
                .builder()
                .setClaims(claims)
                .setExpiration(Date.from(Instant.now().plusSeconds(expiration)))
                .setIssuedAt(Date.from(Instant.now()))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }


}

package com.inuappcenter.team_2_project_server.domain.member.service;

import com.inuappcenter.team_2_project_server.domain.member.entity.Member;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private final SecretKey secretKey;

    private final long accessTokenExpirationMillis;
    private final long refreshTokenExpirationMillis;

    public JwtTokenProvider(
            SecretKey secretKey,
            @Value("${jwt.access-token-expiration-millis}") long accessTokenExpirationMillis,
            @Value("${jwt.refresh-token-expiration-millis}") long refreshTokenExpirationMillis
    ) {
        this.secretKey = secretKey;
        this.accessTokenExpirationMillis = accessTokenExpirationMillis;
        this.refreshTokenExpirationMillis = refreshTokenExpirationMillis;
    }

    /**
     * accessToken 생성 메서드
     */
    public String createAccessToken(Member member) {
        Instant now = Instant.now();
        Instant expiration = now.plusMillis(accessTokenExpirationMillis);

        // 토큰에 member_id, studentNumber, 권한 등을 넣어서 생성
        return Jwts.builder()
                .subject(member.getId().toString())
                .claim("studentNumber", member.getStudentNumber())
                .claim("role", member.getRole())
                .issuedAt(Date.from(now))
                .expiration(Date.from(expiration))
                .signWith(secretKey)
                .compact();
    }

    /**
     * refreshToken 생성 메서드
     */
    public String createRefreshToken(Member member) {
        Instant now = Instant.now();
        Instant expiration = now.plusMillis(refreshTokenExpirationMillis);

        return Jwts.builder()
                .subject(member.getId().toString())
                .issuedAt(Date.from(now))
                .expiration(Date.from(expiration))
                .signWith(secretKey)
                .compact();

    }

    public LocalDateTime getAccessTokenExpiresAt() {
        return LocalDateTime.ofInstant(
                Instant.now().plusMillis(accessTokenExpirationMillis),
                ZoneId.systemDefault()
        );
    }

    public LocalDateTime getRefreshTokenExpiresAt() {
        return LocalDateTime.ofInstant(
                Instant.now().plusMillis(refreshTokenExpirationMillis),
                ZoneId.systemDefault()
        );
    }
}

package com.inuappcenter.team_2_project_server.domain.member.service;

import com.inuappcenter.team_2_project_server.domain.member.entity.Member;
import com.inuappcenter.team_2_project_server.global.error.ex.ErrorCode;
import com.inuappcenter.team_2_project_server.global.error.ex.MyException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * JWT를 만들고, 꺼내고, 검증하고, 해석하는 클래스
 */
@Component
public class JwtTokenProvider {

    private static final String TOKEN_TYPE_CLAIM = "tokenType";     // JWT 내부에 access token과 refresh token을 구분하기 위한 claim 이름
    private static final String ACCESS_TOKEN_TYPE = "ACCESS";       // 보호 API 인증에 사용할 수 있는 토큰 타입
    private static final String REFRESH_TOKEN_TYPE = "REFRESH";     // access token 재발급에만 사용할 토큰 타입

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
                .claim(TOKEN_TYPE_CLAIM, ACCESS_TOKEN_TYPE)
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
                .claim(TOKEN_TYPE_CLAIM, REFRESH_TOKEN_TYPE)
                .issuedAt(Date.from(now))
                .expiration(Date.from(expiration))
                .signWith(secretKey)
                .compact();

    }

    /**
     * accessToken 만료시간 조회 메서드
     */
    public LocalDateTime getAccessTokenExpiresAt() {
        return LocalDateTime.ofInstant(
                Instant.now().plusMillis(accessTokenExpirationMillis),
                ZoneId.systemDefault()
        );
    }

    /**
     * refreshToken 만료시간 조회 메서드
     */
    public LocalDateTime getRefreshTokenExpiresAt() {
        return LocalDateTime.ofInstant(
                Instant.now().plusMillis(refreshTokenExpirationMillis),
                ZoneId.systemDefault()
        );
    }


    /**
     * http요청 Authorization 헤더에서 Bearer를 추출하는 메서드
     * 요청헤더 형식: {Authorization: Bearer abc.def.ghi}
     */
    public String resolveToken(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");

        if (authorization == null || !authorization.startsWith("Bearer ")) {
            return null;
        }

        return authorization.substring(7);
    }


    /**
     * 토큰 검증 메서드
     */
    public void validateAccessToken(String token) {
        Claims claims = parseClaims(token);

        String tokenType = claims.get(TOKEN_TYPE_CLAIM, String.class);
        if (!ACCESS_TOKEN_TYPE.equals(tokenType)) {
            throw new MyException(ErrorCode.TOKEN_INVALID);
        }
    }

    // claim 파싱
    private Claims parseClaims(String token) {
        if (token == null || token.isBlank()) {
            throw new MyException(ErrorCode.TOKEN_MISSING);
        }

        try {
            return Jwts.parser()
                    // JWT parser 설정 시작
                    .verifyWith(secretKey)

                    // Jwts.parser()의 반환값은 builder으므로 build()를 해야 위 설정이 반영된 실제 parser 생성
                    .build()

                    // JWT 형식, 서명, 만료시간, claims 등을 검증
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (ExpiredJwtException e) {
            throw new MyException(ErrorCode.TOKEN_EXPIRED);
        } catch (JwtException | IllegalArgumentException e) {
            throw new MyException(ErrorCode.TOKEN_INVALID);
        }
    }

    // memberId 파싱
    public Long getMemberId(String token) {
        try {
            return Long.valueOf(parseClaims(token).getSubject());
        } catch (NumberFormatException e) {
            throw new MyException(ErrorCode.TOKEN_INVALID);
        }
    }
}

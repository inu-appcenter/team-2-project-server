package com.inuappcenter.team_2_project_server.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

/**
 * JWT 서명/검증에 쓰는 SecretKey Bean을 등록하는 설정 클래스
 */
@Configuration
public class JwtConfig {

    // application.yml의 jwt.secret값을 읽음
    @Value("${jwt.secret}")
    private String secret;

    // 문자열 secret을 HmacSHA256용 SecretKey로 변환
    // JwtTokenProvider가 이 키를 주입받아 토큰 생성과 검증에 사용
    @Bean
    public SecretKey jwtSecretKey() {
        return new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
    }

}

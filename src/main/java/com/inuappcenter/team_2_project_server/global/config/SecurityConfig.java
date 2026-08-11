package com.inuappcenter.team_2_project_server.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        // 기본 웹 보안
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable) // 세션/쿠키 기반 CSRF 보호 비활성화
                .formLogin(AbstractHttpConfigurer::disable) // Spring 기본 로그인 페이지 비활성화
                .httpBasic(AbstractHttpConfigurer::disable) // BasicAuth 팝업/헤더 인증 비활성화
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)); // 서버 세션 생성 비활설화(JWT방식)


        // api 인증
        httpSecurity
                .authorizeHttpRequests(auth -> auth
                        // 최우선 허용
                        .requestMatchers("/api/user/login").permitAll()
                        
                        .anyRequest().permitAll());

        return httpSecurity.build();
    }
}

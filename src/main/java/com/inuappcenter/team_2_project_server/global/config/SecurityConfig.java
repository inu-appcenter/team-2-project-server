package com.inuappcenter.team_2_project_server.global.config;

import com.inuappcenter.team_2_project_server.domain.member.service.JwtFilter;
import com.inuappcenter.team_2_project_server.domain.member.service.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import tools.jackson.databind.ObjectMapper;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsService userDetailsService;
    private final ObjectMapper objectMapper;

    /**
     * 모든 요청이 이 security 필터 체인을 거쳐감
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        // 기본 웹 보안
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable) // 세션/쿠키 기반 CSRF 보호 비활성화
                .formLogin(AbstractHttpConfigurer::disable) // Spring 기본 로그인 페이지 비활성화
                .httpBasic(AbstractHttpConfigurer::disable) // BasicAuth 팝업/헤더 인증 비활성화
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)); // 서버 세션 생성 비활설화(JWT방식)


        // jwt 필터(api 요청 전에 호출되어야 함)
        httpSecurity
                .addFilterBefore(
                        new JwtFilter(jwtTokenProvider, userDetailsService, objectMapper),
                        UsernamePasswordAuthenticationFilter.class);


        // api 인증
        httpSecurity
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**").permitAll()

                        // 최우선 허용
                        .requestMatchers("/api/member/login").permitAll()
                        .anyRequest().authenticated());

        return httpSecurity.build();
    }
}

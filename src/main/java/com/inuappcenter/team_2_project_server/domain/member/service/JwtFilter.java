package com.inuappcenter.team_2_project_server.domain.member.service;

import com.inuappcenter.team_2_project_server.global.dto.ResponseDto;
import com.inuappcenter.team_2_project_server.global.error.ex.MyException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * 매 요청마다 실행되는 JWT 인증 필터
 */
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsService userDetailsService;
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        try {
            // Authorization헤더에서 Bearer 토큰 추출
            String token = jwtTokenProvider.resolveToken(request);

            // 토큰이 있는 요청만 JWT 인증을 시도. 토큰이 없으면 공개 API일 수 있으므로 그냥 통과
            if (token != null) {
                // 토큰의 서명, 형식, 만료 시간을 검증
                jwtTokenProvider.validateAccessToken(token);

                // 토큰 subject에 저장된 회원 id를 꺼냄
                Long memberId = jwtTokenProvider.getMemberId(token);

                // 회원 id로 실제 회원 정보를 조회
                UserDetails userDetails = userDetailsService.loadUserByUsername(memberId.toString());

                // Spring Security가 사용할 인증 객체를 생성
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );

                // 현재 요청을 인증된 사용자 요청으로 등록(이 코드를 통해 Spring Security는 현재 요청을 인증된 요청으로 인식)
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
            
            // 다음 필터 또는 컨트롤러로 요청을 넘김
            filterChain.doFilter(request, response);
        } catch (MyException e) {
            response.setStatus(e.getErrorCode().getStatus().value());
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(
                    objectMapper.writeValueAsString(ResponseDto.error(e.getErrorCode()))
            );
        }
    }
}

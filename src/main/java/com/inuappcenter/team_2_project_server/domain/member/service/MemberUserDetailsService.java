package com.inuappcenter.team_2_project_server.domain.member.service;

import com.inuappcenter.team_2_project_server.domain.member.repository.MemberRepository;
import com.inuappcenter.team_2_project_server.global.error.ex.ErrorCode;
import com.inuappcenter.team_2_project_server.global.error.ex.MyException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * JWT에서 꺼낸 memberId로 실제 회원을 DB에서 찾아오는 클래스
 */
@Service
@RequiredArgsConstructor
public class MemberUserDetailsService implements UserDetailsService {

    public final MemberRepository memberRepository;

    /**
     * Spring Security가 문자열 id를 주면 실제 사용자 객체를 찾아주는 매서드
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // 문자열 username을 받아서 Long으로 변환
        Long memberId = Long.valueOf(username);

        // memberRepository에서 username에 해당하는 유저를 찾음
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new MyException(ErrorCode.MEMBER_NOT_FOUND));
    }
}

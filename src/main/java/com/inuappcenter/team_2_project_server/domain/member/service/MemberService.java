package com.inuappcenter.team_2_project_server.domain.member.service;

import com.inuappcenter.team_2_project_server.domain.member.dto.LoginRequestDto;
import com.inuappcenter.team_2_project_server.domain.member.dto.LoginResponseDto;
import com.inuappcenter.team_2_project_server.domain.member.entity.Member;
import com.inuappcenter.team_2_project_server.domain.member.repository.MemberRepository;
import com.inuappcenter.team_2_project_server.domain.member.repository.SchoolAuthRepository;
import com.inuappcenter.team_2_project_server.global.error.ex.ErrorCode;
import com.inuappcenter.team_2_project_server.global.error.ex.MyException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final SchoolAuthRepository schoolAuthRepository;
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * 로그인 메서드
     */
    @Transactional
    public LoginResponseDto login(LoginRequestDto request) {
        String studentNumber = request.studentNumber();
        String password = request.password();

        if (!schoolAuthRepository.verify(studentNumber, password)) {
            throw new MyException(ErrorCode.INVALID_CREDENTIALS);
        }

        Member member = memberRepository.findByStudentNumber(studentNumber)
                .orElseGet(() -> memberRepository.save(Member.create(studentNumber)));

        String accessToken = jwtTokenProvider.createAccessToken(member);
        String refreshToken = jwtTokenProvider.createRefreshToken(member);

        return new LoginResponseDto(
                accessToken,
                refreshToken,
                jwtTokenProvider.getAccessTokenExpiresAt().toString(),
                jwtTokenProvider.getRefreshTokenExpiresAt().toString()
        );
    }
}

package com.inuappcenter.team_2_project_server.domain.member.service;

import com.inuappcenter.team_2_project_server.domain.member.dto.request.LoginRequestDto;
import com.inuappcenter.team_2_project_server.domain.member.dto.request.MemberCreateRequestDto;
import com.inuappcenter.team_2_project_server.domain.member.dto.request.MemberUpdateRequestDto;
import com.inuappcenter.team_2_project_server.domain.member.dto.response.LoginResponseDto;
import com.inuappcenter.team_2_project_server.domain.member.dto.response.MemberResponseDto;
import com.inuappcenter.team_2_project_server.domain.member.entity.Member;
import com.inuappcenter.team_2_project_server.domain.member.repository.MemberRepository;
import com.inuappcenter.team_2_project_server.domain.member.repository.SchoolAuthRepository;
import com.inuappcenter.team_2_project_server.global.error.ex.ErrorCode;
import com.inuappcenter.team_2_project_server.global.error.ex.MyException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
                .orElseGet(() -> memberRepository.save(Member.create(studentNumber, studentNumber, null, null)));

        String accessToken = jwtTokenProvider.createAccessToken(member);
        String refreshToken = jwtTokenProvider.createRefreshToken(member);

        return new LoginResponseDto(
                accessToken,
                refreshToken,
                jwtTokenProvider.getAccessTokenExpiresAt().toString(),
                jwtTokenProvider.getRefreshTokenExpiresAt().toString()
        );
    }

    /**
     * 유저 전체 조회
     */
    @Transactional(readOnly = true)
    public List<MemberResponseDto> getMemberAll() {
        List<MemberResponseDto> members = memberRepository.findAll()
                .stream()
                .map(MemberResponseDto::from)
                .toList();

        return members;
    }

    /**
     * 유저 단일 조회
     */
    @Transactional(readOnly = true)
    public MemberResponseDto getMember(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MyException(ErrorCode.MEMBER_NOT_FOUND));

        return MemberResponseDto.from(member);
    }


    /**
     * 유저 프로필 변경 메서드
     */
    @Transactional
    public MemberResponseDto updateMemberProfile(Long memberId, MemberUpdateRequestDto request) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MyException(ErrorCode.MEMBER_NOT_FOUND));

        member.updateMemberProfile(
                request.nickName(),
                request.department(),
                request.email()
        );

        return MemberResponseDto.from(member);
    }

    /**
     * 유저 생성
     */
    @Transactional
    public MemberResponseDto createMember(MemberCreateRequestDto request) {
        if (memberRepository.existsByStudentNumber(request.studentNumber())) {
            throw new MyException(ErrorCode.INVALID_INPUT);
        }


        Member member = Member.create(
                request.studentNumber(),
                request.nickName(),
                request.department(),
                request.email()
        );

        Member savedMember = memberRepository.save(member);

        return MemberResponseDto.from(savedMember);
    }


    /**
     * 유저 삭제
     */
    @Transactional
    public void deleteMember(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MyException(ErrorCode.MEMBER_NOT_FOUND));

        memberRepository.delete(member);
    }
}

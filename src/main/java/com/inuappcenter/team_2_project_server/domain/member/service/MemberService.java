package com.inuappcenter.team_2_project_server.domain.member.service;

import com.inuappcenter.team_2_project_server.domain.member.dto.LocalAuthLoginDto;
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

        LocalAuthLoginDto authResult = schoolAuthRepository.authenticate(studentNumber, password)
                .orElseThrow(() -> new MyException(ErrorCode.INVALID_CREDENTIALS));

        Member member = memberRepository.findByStudentNumber(studentNumber)
                .orElseGet(() -> memberRepository.save(
                        Member.createWithRole(
                                authResult.studentNumber(),
                                authResult.studentNumber(),
                                null,
                                null,
                                null,
                                authResult.role()
                        )
                ));

        member.recordLogin();

        String accessToken = jwtTokenProvider.createAccessToken(member);
        String refreshToken = jwtTokenProvider.createRefreshToken(member);

        // isNew 는 온보딩 완료 여부. 완료 시 프론트가 PATCH /api/member/is-new 로 내린다
        return new LoginResponseDto(
                accessToken,
                refreshToken,
                jwtTokenProvider.getAccessTokenExpiresAt().toString(),
                jwtTokenProvider.getRefreshTokenExpiresAt().toString(),
                member.getId(),
                member.isNew()
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
                request.college(),
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

    /**
     * 온보딩 완료 처리 - isNew 플래그를 내린다. 프론트가 온보딩 마지막 단계에서 호출
     */
    @Transactional
    public MemberResponseDto completeOnboarding(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MyException(ErrorCode.MEMBER_NOT_FOUND));

        member.updateIsNew();

        return MemberResponseDto.from(member);
    }
}

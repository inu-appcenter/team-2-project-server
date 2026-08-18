package com.inuappcenter.team_2_project_server.domain.member.controller;

import com.inuappcenter.team_2_project_server.domain.member.dto.request.LoginRequestDto;
import com.inuappcenter.team_2_project_server.domain.member.dto.request.MemberCreateRequestDto;
import com.inuappcenter.team_2_project_server.domain.member.dto.request.MemberUpdateRequestDto;
import com.inuappcenter.team_2_project_server.domain.member.dto.response.LoginResponseDto;
import com.inuappcenter.team_2_project_server.domain.member.dto.response.MemberResponseDto;
import com.inuappcenter.team_2_project_server.domain.member.entity.Member;
import com.inuappcenter.team_2_project_server.domain.member.service.MemberService;
import com.inuappcenter.team_2_project_server.global.dto.ResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/member")
public class MemberController {

    private final MemberService memberService;

    /**
     * 로그인 컨트롤러
     */
    @PostMapping("/login")
    public ResponseEntity<ResponseDto<LoginResponseDto>> login(
            @Valid @RequestBody LoginRequestDto request
    ) {

        LoginResponseDto response = memberService.login(request);

        return ResponseEntity.ok(
                ResponseDto.of(response, "로그인 성공")
        );
    }

    /**
     * 유저 생성 컨트롤러
     */
    @PostMapping()
    public ResponseEntity<ResponseDto<MemberResponseDto>> createMember(
            @Valid @RequestBody MemberCreateRequestDto request
    ) {
        MemberResponseDto response = memberService.createMember(request);

        return ResponseEntity.ok(ResponseDto.of(response, "유저 생성 성공"));
    }

    /**
     * 유저 전체 조회 컨트롤러
     */
    @GetMapping
    public ResponseEntity<ResponseDto<List<MemberResponseDto>>> getMemberAll() {
        List<MemberResponseDto> responses = memberService.getMemberAll();
        return ResponseEntity.ok(ResponseDto.of(responses, "전체 유저 조회 성공"));
    }

    /**
     * 유저 단일 조회 컨트롤러
     */
    @GetMapping("/{memberId}")
    public ResponseEntity<ResponseDto<MemberResponseDto>> getMember(
            @PathVariable Long memberId
    ) {
        MemberResponseDto response = memberService.getMember(memberId);

        return ResponseEntity.ok(ResponseDto.of(response, "유저 조회 성공"));
    }

    /**
     * 유저 프로필 수정 컨트롤러
     */
    @PatchMapping
    public ResponseEntity<ResponseDto<MemberResponseDto>> updateMember(
            @AuthenticationPrincipal Member member,
            @RequestBody MemberUpdateRequestDto request
    ) {
        MemberResponseDto response = memberService.updateMemberProfile(member.getId(), request);

        return ResponseEntity.ok(ResponseDto.of(response, "유저 프로필 수정 성공"));
    }

    /**
     * 유저 삭제 컨트롤러
     */
    @DeleteMapping
    public ResponseEntity<ResponseDto<Long>> deleteMember(
            @AuthenticationPrincipal Member member
    ) {
        memberService.deleteMember(member.getId());
        return ResponseEntity.ok(ResponseDto.of(member.getId(), "유저 삭제 성공"));
    }
}

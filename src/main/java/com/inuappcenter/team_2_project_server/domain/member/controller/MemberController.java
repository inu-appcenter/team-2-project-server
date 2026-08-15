package com.inuappcenter.team_2_project_server.domain.member.controller;

import com.inuappcenter.team_2_project_server.domain.member.dto.LoginRequestDto;
import com.inuappcenter.team_2_project_server.domain.member.dto.LoginResponseDto;
import com.inuappcenter.team_2_project_server.domain.member.service.MemberService;
import com.inuappcenter.team_2_project_server.global.dto.ResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}

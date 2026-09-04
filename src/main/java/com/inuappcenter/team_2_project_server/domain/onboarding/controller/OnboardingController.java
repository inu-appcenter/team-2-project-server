package com.inuappcenter.team_2_project_server.domain.onboarding.controller;

import com.inuappcenter.team_2_project_server.domain.member.dto.response.MemberResponseDto;
import com.inuappcenter.team_2_project_server.domain.member.entity.Member;
import com.inuappcenter.team_2_project_server.domain.onboarding.dto.OnboardingRequestDto;
import com.inuappcenter.team_2_project_server.domain.onboarding.service.OnboardingService;
import com.inuappcenter.team_2_project_server.global.dto.ResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/onboarding")
public class OnboardingController implements OnboardingApiSpecification {

    private final OnboardingService onboardingService;

    @Override
    @PostMapping
    public ResponseEntity<ResponseDto<MemberResponseDto>> complete(
            @AuthenticationPrincipal Member member,
            @Valid @RequestBody OnboardingRequestDto request
    ) {
        MemberResponseDto response = onboardingService.complete(member.getId(), request);

        return ResponseEntity.ok(ResponseDto.of(response, "온보딩 완료"));
    }
}

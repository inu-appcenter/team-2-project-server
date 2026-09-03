package com.inuappcenter.team_2_project_server.domain.member.controller;

import com.inuappcenter.team_2_project_server.domain.member.dto.request.ResearcherRegisterRequestDto;
import com.inuappcenter.team_2_project_server.domain.member.dto.response.ResearcherResponseDto;
import com.inuappcenter.team_2_project_server.domain.member.service.ResearcherService;
import com.inuappcenter.team_2_project_server.global.dto.ResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/researcher")
public class ResearcherController {

    private final ResearcherService researcherService;

    /**
     * 연구자 등록 컨트롤러
     */
    @PostMapping
    public ResponseEntity<ResponseDto<ResearcherResponseDto>> register(
            @Valid @RequestBody ResearcherRegisterRequestDto request
    ) {
        ResearcherResponseDto response = researcherService.register(
                request.memberId(),
                request.laboratoryId(),
                request.name()
        );

        return ResponseEntity.ok(
                ResponseDto.of(response, "연구자 등록 성공")
        );
    }

    /**
     * 연구자 조회 컨트롤러 (memberId 기준)
     */
    @GetMapping
    public ResponseEntity<ResponseDto<ResearcherResponseDto>> getResearcher(
            @RequestParam Long memberId
    ) {
        ResearcherResponseDto response = researcherService.getByMemberId(memberId);

        return ResponseEntity.ok(
                ResponseDto.of(response, "연구자 조회 성공")
        );
    }
}

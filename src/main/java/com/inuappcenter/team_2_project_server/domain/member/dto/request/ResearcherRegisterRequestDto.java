package com.inuappcenter.team_2_project_server.domain.member.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * 연구자 등록 요청 DTO
 */
public record ResearcherRegisterRequestDto(
        @NotNull Long memberId,
        @NotNull Long laboratoryId,
        @NotBlank String name
) {
}

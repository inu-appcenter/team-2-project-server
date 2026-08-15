package com.inuappcenter.team_2_project_server.domain.member.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginRequestDto(
        @NotBlank String studentNumber,
        @NotBlank String password
) {
}

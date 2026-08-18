package com.inuappcenter.team_2_project_server.domain.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record LoginRequestDto(
        @Schema(description = "학번", example = "202412345")
        @NotBlank
        String studentNumber,

        @Schema(description = "비밀번호", example = "password1234")
        @NotBlank
        String password
) {
}

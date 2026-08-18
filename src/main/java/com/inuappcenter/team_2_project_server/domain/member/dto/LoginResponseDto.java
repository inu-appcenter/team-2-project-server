package com.inuappcenter.team_2_project_server.domain.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record LoginResponseDto(
        @Schema(description = "액세스 토큰", example = "eyJhbGciOiJIUzI1NiJ9...")
        String accessToken,

        @Schema(description = "리프레시 토큰", example = "eyJhbGciOiJIUzI1NiJ9...")
        String refreshToken,

        @Schema(description = "액세스 토큰 만료 시각", example = "2026-08-18T12:00:00")
        String accessTokenExpiresAt,

        @Schema(description = "리프레시 토큰 만료 시각", example = "2026-08-25T12:00:00")
        String refreshTokenExpiresAt
) {
}

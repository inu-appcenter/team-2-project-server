package com.inuappcenter.team_2_project_server.domain.member.dto;

public record LoginResponseDto(
        String accessToken,
        String refreshToken,
        String accessTokenExpiresAt,
        String refreshTokenExpiresAt
) {
}

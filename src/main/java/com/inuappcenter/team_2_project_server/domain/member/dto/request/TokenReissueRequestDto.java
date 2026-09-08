package com.inuappcenter.team_2_project_server.domain.member.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

/**
 * accessToken 재발급 요청 DTO.
 * 로그인 시 받은 refreshToken 을 그대로 담아 보낸다. (Authorization 헤더는 보내지 않음)
 */
public record TokenReissueRequestDto(

        @NotBlank
        @Schema(description = "로그인 시 발급받은 refreshToken", example = "eyJhbGciOiJIUzI1NiJ9...")
        String refreshToken
) {
}

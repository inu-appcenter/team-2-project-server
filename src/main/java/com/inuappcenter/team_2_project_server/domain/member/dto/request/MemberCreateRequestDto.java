package com.inuappcenter.team_2_project_server.domain.member.dto.request;

import com.inuappcenter.team_2_project_server.domain.department.College;
import com.inuappcenter.team_2_project_server.domain.department.Department;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MemberCreateRequestDto(
        @NotBlank String studentNumber,
        @NotBlank String nickName,
        @NotNull College college,
        @NotNull Department department,
        String email
) {
}

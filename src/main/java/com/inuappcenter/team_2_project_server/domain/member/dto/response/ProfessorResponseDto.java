package com.inuappcenter.team_2_project_server.domain.member.dto.response;

import com.inuappcenter.team_2_project_server.domain.department.College;
import com.inuappcenter.team_2_project_server.domain.department.Department;

public record ProfessorResponseDto(
        Long id,
        String positionRaw,
        College college,
        Department department,
        String name,
        String phoneNumber,
        String email
) {
}

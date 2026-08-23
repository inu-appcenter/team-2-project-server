package com.inuappcenter.team_2_project_server.domain.laboratory.dto;

import com.inuappcenter.team_2_project_server.domain.department.College;
import com.inuappcenter.team_2_project_server.domain.department.Department;
import com.inuappcenter.team_2_project_server.domain.member.dto.response.ProfessorResponseDto;

public record LaboratoryResponseDto(
        Long id,
        College college,
        Department department,
        String labName,
        String location,
        Long capacity,
        String introduction,
        ProfessorResponseDto professor,
        String labUrl,
        String researchFieldRaw
) {
}

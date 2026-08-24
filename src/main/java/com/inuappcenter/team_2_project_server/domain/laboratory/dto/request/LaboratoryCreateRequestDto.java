package com.inuappcenter.team_2_project_server.domain.laboratory.dto.request;

import com.inuappcenter.team_2_project_server.domain.department.College;
import com.inuappcenter.team_2_project_server.domain.department.Department;

import java.util.List;

public record LaboratoryCreateRequestDto(
        College college,
        Department department,
        String labName,
        String location,
        Long capacity,
        String introduction,
        Long professorId,
        String labUrl,
        List<String> researchAreas
) {
}

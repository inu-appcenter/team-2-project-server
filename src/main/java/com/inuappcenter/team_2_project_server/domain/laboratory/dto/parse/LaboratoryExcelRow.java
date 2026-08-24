package com.inuappcenter.team_2_project_server.domain.laboratory.dto.parse;

import com.inuappcenter.team_2_project_server.domain.department.College;
import com.inuappcenter.team_2_project_server.domain.department.Department;

public record LaboratoryExcelRow(
        College college,
        Department department,
        String labName,
        String professorName,
        String professorEmail,
        String labUrl
) {
}

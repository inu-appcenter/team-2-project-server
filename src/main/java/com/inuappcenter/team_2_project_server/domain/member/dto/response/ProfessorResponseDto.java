package com.inuappcenter.team_2_project_server.domain.member.dto.response;

import com.inuappcenter.team_2_project_server.domain.department.College;
import com.inuappcenter.team_2_project_server.domain.department.Department;
import com.inuappcenter.team_2_project_server.domain.member.entity.Professor;

public record ProfessorResponseDto(
        Long id,
        String positionRaw,
        College college,
        Department department,
        String name,
        String phoneNumber,
        String email
) {

    public static ProfessorResponseDto from(Professor professor) {
        return new ProfessorResponseDto(
                professor.getId(),
                professor.getPositionRaw(),
                professor.getCollege(),
                professor.getDepartment(),
                professor.getName(),
                professor.getPhoneNumber(),
                professor.getEmail()
        );
    }
}

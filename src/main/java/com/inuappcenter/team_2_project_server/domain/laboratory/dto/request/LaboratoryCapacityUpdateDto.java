package com.inuappcenter.team_2_project_server.domain.laboratory.dto.request;

public record LaboratoryCapacityUpdateDto(
        Integer graduateStudentCount,
        Integer undergraduateStudentCount
) {
}

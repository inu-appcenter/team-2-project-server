package com.inuappcenter.team_2_project_server.domain.laboratory.dto.request;

import com.inuappcenter.team_2_project_server.domain.department.College;
import com.inuappcenter.team_2_project_server.domain.department.Department;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record LaboratoryCreateRequestDto(
        @NotNull College college,
        @NotNull Department department,
        @NotBlank String labName,
        @NotBlank String location,
        @Valid @NotNull LaboratoryCapacityCreateDto capacity,
        String introduction,
        @NotNull Long professorId,
        @NotBlank String labUrl,
        List<String> researchAreas
) {
}

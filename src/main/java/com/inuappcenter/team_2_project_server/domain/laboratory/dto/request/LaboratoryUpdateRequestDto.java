package com.inuappcenter.team_2_project_server.domain.laboratory.dto.request;

import jakarta.validation.Valid;

import java.util.List;

public record LaboratoryUpdateRequestDto(
        String labName,
        String location,
        @Valid LaboratoryCapacityUpdateDto capacity,
        String introduction,
        String labUrl,
        List<String> researchAreas
) {
}

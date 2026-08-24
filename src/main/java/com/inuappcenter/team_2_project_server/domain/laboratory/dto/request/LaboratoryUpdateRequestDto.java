package com.inuappcenter.team_2_project_server.domain.laboratory.dto.request;

import java.util.List;

public record LaboratoryUpdateRequestDto(
        String labName,
        String location,
        Long capacity,
        String introduction,
        String labUrl,
        List<String> researchAreas
) {
}

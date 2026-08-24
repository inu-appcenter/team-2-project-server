package com.inuappcenter.team_2_project_server.domain.laboratory.dto.request;

public record LaboratoryUpdateRequestDto(
        String labName,
        String location,
        Long capacity,
        String introduction,
        String labUrl,
        String researchFieldRaw
) {
}

package com.inuappcenter.team_2_project_server.domain.laboratory.dto.request;

import com.inuappcenter.team_2_project_server.domain.laboratory.dto.LaboratoryCapacityDto;

import java.util.List;

public record LaboratoryUpdateRequestDto(
        String labName,
        String location,
        LaboratoryCapacityDto capacity,
        String introduction,
        String labUrl,
        List<String> researchAreas
) {
}

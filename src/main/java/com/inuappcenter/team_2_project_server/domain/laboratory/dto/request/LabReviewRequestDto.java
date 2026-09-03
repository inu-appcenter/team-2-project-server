package com.inuappcenter.team_2_project_server.domain.laboratory.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.Set;

public record LabReviewRequestDto(
        @NotBlank String coreTime,
        @NotBlank String weeklyMeeting,
        @NotEmpty Set<@NotBlank String> doings
) {
}

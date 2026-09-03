package com.inuappcenter.team_2_project_server.domain.laboratory.dto.response;

import com.inuappcenter.team_2_project_server.domain.laboratory.entity.LabReview;

import java.util.Set;

public record LabReviewResponseDto(
        Long id,
        Long laboratoryId,
        String coreTime,
        String weeklyMeeting,
        Set<String> doings
) {
    public static LabReviewResponseDto from(LabReview labReview) {
        return new LabReviewResponseDto(
                labReview.getId(),
                labReview.getLaboratory().getId(),
                labReview.getCoreTime(),
                labReview.getWeeklyMeeting(),
                labReview.getDoings()
        );
    }
}

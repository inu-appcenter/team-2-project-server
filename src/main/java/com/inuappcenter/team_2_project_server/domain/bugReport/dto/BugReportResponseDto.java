package com.inuappcenter.team_2_project_server.domain.bugReport.dto;

import com.inuappcenter.team_2_project_server.domain.bugReport.entity.BugReport;
import com.inuappcenter.team_2_project_server.domain.bugReport.enums.Category;

public record BugReportResponseDto(
        Long id,
        Category category,
        String categoryDescription,
        String content,
        String email
) {

    public static BugReportResponseDto from(BugReport bugReport) {
        return new BugReportResponseDto(
                bugReport.getId(),
                bugReport.getCategory(),
                bugReport.getCategory().getDescription(),
                bugReport.getContent(),
                bugReport.getEmail()
        );
    }
}

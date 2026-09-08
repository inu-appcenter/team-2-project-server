package com.inuappcenter.team_2_project_server.domain.bugReport.dto;

import com.inuappcenter.team_2_project_server.domain.bugReport.enums.Category;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record BugReportRequestDto(
        @NotNull Category category,
        @NotBlank @Size(max = 1000) String content,
        @Email String email
) {
}

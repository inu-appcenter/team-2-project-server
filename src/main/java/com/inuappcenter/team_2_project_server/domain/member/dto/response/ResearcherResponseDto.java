package com.inuappcenter.team_2_project_server.domain.member.dto.response;

import com.inuappcenter.team_2_project_server.domain.member.entity.Researcher;

public record ResearcherResponseDto(
        Long id,
        Long memberId,
        String studentNumber,
        Long laboratoryId,
        String laboratoryName,
        boolean validateYN
) {
    public static ResearcherResponseDto from(Researcher researcher) {
        return new ResearcherResponseDto(
                researcher.getId(),
                researcher.getMember().getId(),
                researcher.getMember().getStudentNumber(),
                researcher.getLaboratory().getId(),
                researcher.getLaboratory().getLabName(),
                researcher.isValidateYN()
        );
    }
}

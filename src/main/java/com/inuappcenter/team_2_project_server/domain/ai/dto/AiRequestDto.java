package com.inuappcenter.team_2_project_server.domain.ai.dto;

import java.util.List;

public record AiRequestDto(
        List<Message> messages
) {
    public record Message(
            String role,
            String content
    ) {
    }
}

package com.inuappcenter.team_2_project_server.domain.ai.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record AiResponseDto(
        String id,
        String object,
        List<Choice> choices,
        Double credits,
        List<FileInfo> files
) {
    public record Choice(
            Message message,
            @JsonProperty("finish_reason")
            String finishReason
    ) {
    }

    public record Message(
            String role,
            String content
    ) {
    }

    public record FileInfo(
            @JsonProperty("file_id")
            String fileId,
            String filename,
            @JsonProperty("mime_type")
            String mimeType,
            String url,
            @JsonProperty("expires_in")
            Integer expiresIn
    ) {
    }
}

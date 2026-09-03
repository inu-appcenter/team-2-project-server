package com.inuappcenter.team_2_project_server.domain.ai.service;

import com.inuappcenter.team_2_project_server.domain.ai.dto.AiRequestDto;
import com.inuappcenter.team_2_project_server.domain.ai.dto.AiResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class AiService {

    private final WebClient aiWebClient;
    private final String chatBotId;

    public AiService(
            WebClient aiWebClient,
            @Value("${ai.chatbot-id}") String chatBotId
    ) {
        this.aiWebClient = aiWebClient;
        this.chatBotId = chatBotId;
    }

    public AiResponseDto ask(String question) {
        AiRequestDto request = new AiRequestDto(
                List.of(
                        new AiRequestDto.Message("user", question)
                )
        );

        return aiWebClient
                .post()
                .uri("/v1/gateway/chatbots/{chatbotId}/chat/completions/", chatBotId)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(AiResponseDto.class)
                .block();
    }
}

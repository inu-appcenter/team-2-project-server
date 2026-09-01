package com.inuappcenter.team_2_project_server.domain.laboratory.dto.request;

import com.inuappcenter.team_2_project_server.domain.laboratory.enums.ContactType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CoffeeChatUpdateRequestDto(
        @NotNull ContactType contactType,
        @NotBlank String contactValue
) {
}

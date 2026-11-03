package com.command.toyvillage_server.domain.app.team.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;

public record TeamRequest(
        @NotBlank(message = "공백으로 둘 순 없습니다.")
        String name
) {
}

package com.command.toyvillage_server.domain.app.join_team.presentation.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record TeamRequest(
        @NotEmpty(message = "팀에 배정할 유저를 선택해주세요.")
        List<@NotNull(message = "유저 ID를 입력해주세요.") Long> appAdminIds
) {
}

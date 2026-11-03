package com.command.toyvillage_server.domain.web.auth.admin.presentation.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public record WebAdminChangePasswordRequest(
        @NotBlank(message = "비밀번호 변경 권한 토큰을 비워둘 수 없습니다.")
        @JsonProperty("reset_token")
        String resetToken,

        @NotBlank(message = "새 비밀번호를 비워둘 순 없습니다.")
        @JsonProperty("new_password")
        String newPassword
) {
}

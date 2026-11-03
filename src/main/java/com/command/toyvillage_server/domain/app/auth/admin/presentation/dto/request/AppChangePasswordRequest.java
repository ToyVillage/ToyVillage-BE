package com.command.toyvillage_server.domain.app.auth.admin.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;

public record AppChangePasswordRequest(
        @NotBlank(message = "현재 비밀번호를 비워둘 수 없습니다.")
        String currentPassword,

        @NotBlank(message = "새 비밀번호를 비워둘 수 없습니다.")
        String newPassword
) {
}

package com.command.toyvillage_server.domain.web.auth.admin.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;

public record WebAdminVerifyEmailCodeRequest(
        @NotBlank(message = "인증코드를 입력해주세요.")
        String code,

        @NotBlank(message = "이메일을 비워둘 순 없습니다.")
        String email
) {
}

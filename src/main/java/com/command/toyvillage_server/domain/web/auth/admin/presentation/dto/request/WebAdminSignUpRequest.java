package com.command.toyvillage_server.domain.web.auth.admin.presentation.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record WebAdminSignUpRequest(
        @Email(message = "올바른 이메일 형식으로 입력해주세요.")
        @NotBlank(message = "이메일을 공백으로 둘 순 없습니다.")
        String email,

        @NotBlank(message = "비밀번호를 공백으로 둘 순 없습니다.")
        String password
) {
}

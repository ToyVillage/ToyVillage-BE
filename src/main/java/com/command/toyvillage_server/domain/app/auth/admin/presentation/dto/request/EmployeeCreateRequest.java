package com.command.toyvillage_server.domain.app.auth.admin.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record EmployeeCreateRequest(
        @NotBlank(message = "아이디를 비워둘 수 없습니다.")
        String username,

        @NotBlank(message = "이름을 비워둘 수 없습니다.")
        String name,

        @Size(max = 30, message = "직급은 30자를 넘을 수 없습니다.")
        String position
) {
}

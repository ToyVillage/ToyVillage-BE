package com.command.toyvillage_server.domain.app.auth.admin.presentation.dto.request;

import com.command.toyvillage_server.domain.app.auth.admin.domain.AppAdminRole;
import jakarta.validation.constraints.NotNull;

public record EmployeeRoleChangeRequest(
        @NotNull(message = "권한을 비워둘 수 없습니다.")
        AppAdminRole role
) {
}

package com.command.toyvillage_server.domain.app.auth.admin.presentation.dto.response;

import com.command.toyvillage_server.domain.app.auth.admin.domain.AppAdmin;
import com.command.toyvillage_server.domain.app.auth.admin.domain.AppAdminRole;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record EmployeeResponse(
        Long id,
        String username,
        String name,
        AppAdminRole role,
        LocalDate createAt,
        boolean passwordChanged
) {
    public static EmployeeResponse from(AppAdmin employee) {
        return EmployeeResponse.builder()
                .id(employee.getId())
                .username(employee.getUsername())
                .name(employee.getName())
                .role(employee.getRole())
                .createAt(employee.getCreateAt())
                .passwordChanged(employee.isPasswordChanged())
                .build();
    }
}
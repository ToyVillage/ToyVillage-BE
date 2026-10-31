package com.command.toyvillage_server.domain.app.auth.admin.domain;

import java.util.List;

public enum AppAdminRole {
    APP_ADMIN,
    MIDDLE_ADMIN,
    EMPLOYEE;

    public static final List<AppAdminRole> EMPLOYEE_ROLES = List.of(EMPLOYEE, MIDDLE_ADMIN);
}

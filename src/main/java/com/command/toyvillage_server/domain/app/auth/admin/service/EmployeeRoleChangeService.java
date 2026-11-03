package com.command.toyvillage_server.domain.app.auth.admin.service;

import com.command.toyvillage_server.domain.app.auth.admin.domain.AppAdmin;
import com.command.toyvillage_server.domain.app.auth.admin.domain.AppAdminRole;
import com.command.toyvillage_server.domain.app.auth.admin.domain.repository.AppAdminRepository;
import com.command.toyvillage_server.domain.app.auth.admin.exception.AppAdminNotFoundException;
import com.command.toyvillage_server.domain.app.auth.admin.presentation.dto.request.EmployeeRoleChangeRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EmployeeRoleChangeService {
    private final AppAdminRepository appAdminRepository;

    @Transactional
    public void execute(Long appAdminId, EmployeeRoleChangeRequest request) {
        AppAdmin appAdmin = appAdminRepository.findByIdAndRoleInAndDeleteStatusFalse(appAdminId, AppAdminRole.EMPLOYEE_ROLES)
                .orElseThrow(() -> AppAdminNotFoundException.EXCEPTION);

        appAdmin.changeRole(request.role());
    }
}

package com.command.toyvillage_server.domain.app.auth.admin.service;

import com.command.toyvillage_server.domain.app.auth.admin.domain.AppAdmin;
import com.command.toyvillage_server.domain.app.auth.admin.domain.repository.AppAdminRepository;
import com.command.toyvillage_server.domain.app.auth.admin.exception.AppAdminNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EmployeeDeleteService {
    private final AppAdminRepository appAdminRepository;

    @Transactional
    public void execute(Long appAdminId) {
        AppAdmin appAdmin = appAdminRepository.findById(appAdminId)
            .orElseThrow(() -> AppAdminNotFoundException.EXCEPTION);

        appAdmin.changeDeleteStatus();
    }
}

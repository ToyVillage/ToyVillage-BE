package com.command.toyvillage_server.domain.app.auth.admin.service;

import com.command.toyvillage_server.domain.app.auth.admin.domain.AppAdmin;
import com.command.toyvillage_server.domain.app.auth.admin.domain.repository.AppAdminRepository;
import com.command.toyvillage_server.domain.app.auth.admin.exception.AppAdminAlreadyExistsException;
import com.command.toyvillage_server.domain.app.auth.admin.presentation.dto.request.EmployeeCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EmployeeCreateService {
    private final AppAdminRepository appAdminRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void execute(EmployeeCreateRequest request) {
        if (appAdminRepository.existsByUsername(request.username())) {
            throw AppAdminAlreadyExistsException.EXCEPTION;
        }

        AppAdmin employee = AppAdmin.createEmployee(
                request.username(),
                request.name(),
                passwordEncoder.encode(request.username()),
                request.position()
        );
        appAdminRepository.save(employee);
    }
}

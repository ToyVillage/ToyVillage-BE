package com.command.toyvillage_server.domain.app.auth.admin.service;

import com.command.toyvillage_server.domain.app.auth.admin.domain.AppAdminRole;
import com.command.toyvillage_server.domain.app.auth.admin.domain.repository.AppAdminRepository;
import com.command.toyvillage_server.domain.app.auth.admin.presentation.dto.response.EmployeeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QueryEmployeeListService {
    private final AppAdminRepository appAdminRepository;

    @Transactional(readOnly = true)
    public List<EmployeeResponse> execute() {
        return appAdminRepository.findAllByRoleOrderByIdAsc(AppAdminRole.EMPLOYEE)
                .stream()
                .map(EmployeeResponse::from)
                .toList();
    }
}

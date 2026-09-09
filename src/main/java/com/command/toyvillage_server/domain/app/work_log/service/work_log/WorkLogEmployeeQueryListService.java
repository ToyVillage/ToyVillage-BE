package com.command.toyvillage_server.domain.app.work_log.service.work_log;

import com.command.toyvillage_server.domain.app.auth.admin.exception.AppAdminNotFoundException;
import com.command.toyvillage_server.domain.app.work_log.domain.repository.WorkLogRepository;
import com.command.toyvillage_server.domain.app.work_log.presentation.dto.response.WorkLogListResponse;
import com.command.toyvillage_server.global.security.auth.AppAdminDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WorkLogEmployeeQueryListService {
    private final WorkLogRepository workLogRepository;

    @Transactional(readOnly = true)
    public Page<WorkLogListResponse> execute(Pageable pageable) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !(authentication.getPrincipal() instanceof AppAdminDetails appAdminDetails)) {
            throw AppAdminNotFoundException.EXCEPTION;
        }

        return workLogRepository.findByAppAdminId(appAdminDetails.getId(), pageable)
            .map(WorkLogListResponse::from);
    }
}

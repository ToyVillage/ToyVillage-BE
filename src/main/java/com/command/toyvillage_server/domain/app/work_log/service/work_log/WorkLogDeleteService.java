package com.command.toyvillage_server.domain.app.work_log.service.work_log;

import com.command.toyvillage_server.domain.app.auth.admin.exception.AppAdminNotFoundException;
import com.command.toyvillage_server.domain.app.work_log.domain.WorkLog;
import com.command.toyvillage_server.domain.app.work_log.domain.repository.WorkLogRepository;
import com.command.toyvillage_server.domain.app.work_log.exception.WorkLogForbiddenException;
import com.command.toyvillage_server.domain.app.work_log.exception.WorkLogNotFoundException;
import com.command.toyvillage_server.global.security.auth.AppAdminDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WorkLogDeleteService {
    private final WorkLogRepository workLogRepository;

    @Transactional
    public void execute(Long workLogId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !(authentication.getPrincipal() instanceof AppAdminDetails appAdminDetails)) {
            throw AppAdminNotFoundException.EXCEPTION;
        }

        WorkLog workLog = workLogRepository.findById(workLogId)
            .orElseThrow(() -> WorkLogNotFoundException.EXCEPTION);

        if (!appAdminDetails.isAdmin() && !workLog.isWrittenBy(appAdminDetails.getId())) {
            throw WorkLogForbiddenException.EXCEPTION;
        }

        workLogRepository.delete(workLog);
    }
}

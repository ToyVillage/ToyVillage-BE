package com.command.toyvillage_server.domain.app.work_log.service.work_log;

import com.command.toyvillage_server.domain.app.work_log.domain.WorkLog;
import com.command.toyvillage_server.domain.app.work_log.domain.repository.WorkLogRepository;
import com.command.toyvillage_server.domain.app.work_log.presentation.dto.response.WorkLogListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class WorkLogAdminQueryListService {
    private final WorkLogRepository workLogRepository;

    @Transactional(readOnly = true)
    public Page<WorkLogListResponse> execute(LocalDate date, Long templateId, Pageable pageable) {
        Page<WorkLog> workLogs;

        if (templateId == null) {
            workLogs = workLogRepository.findByWriteAt(date, pageable);
        } else {
            workLogs = workLogRepository.findByWriteAtAndTemplateId(date, templateId, pageable);
        }

        return workLogs.map(WorkLogListResponse::from);
    }
}

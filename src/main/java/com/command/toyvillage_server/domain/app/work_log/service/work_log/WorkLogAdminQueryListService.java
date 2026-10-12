package com.command.toyvillage_server.domain.app.work_log.service.work_log;

import com.command.toyvillage_server.domain.app.work_log.domain.WorkLog;
import com.command.toyvillage_server.domain.app.work_log.domain.WorkLogTemplate;
import com.command.toyvillage_server.domain.app.work_log.domain.repository.WorkLogRepository;
import com.command.toyvillage_server.domain.app.work_log.domain.repository.WorkLogTemplateRepository;
import com.command.toyvillage_server.domain.app.work_log.presentation.dto.response.WorkLogListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkLogAdminQueryListService {
    private final WorkLogRepository workLogRepository;
    private final WorkLogTemplateRepository workLogTemplateRepository;

    @Transactional(readOnly = true)
    public Page<WorkLogListResponse> execute(LocalDate date, Long templateId, Pageable pageable) {
        Page<WorkLog> workLogs;

        if (templateId == null) {
            workLogs = workLogRepository.findByWriteAt(date, pageable);
        } else {
            List<Long> templateIds = getTemplateIds(templateId);
            if (templateIds.isEmpty()) {
                return Page.empty(pageable);
            }
            workLogs = workLogRepository.findByWriteAtAndTemplateIdIn(date, templateIds, pageable);
        }

        return workLogs.map(WorkLogListResponse::from);
    }

    private List<Long> getTemplateIds(Long templateId) {
        WorkLogTemplate template = workLogTemplateRepository.findById(templateId).orElse(null);
        if (template == null) {
            return List.of();
        }

        Long originalTemplateId = template.getHistoryId();
        return workLogTemplateRepository.findAllByIdOrOriginalTemplateId(originalTemplateId, originalTemplateId)
            .stream()
            .map(WorkLogTemplate::getId)
            .toList();
    }
}

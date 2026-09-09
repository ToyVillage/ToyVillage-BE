package com.command.toyvillage_server.domain.app.work_log.service.template;

import com.command.toyvillage_server.domain.app.work_log.domain.WorkLogTemplate;
import com.command.toyvillage_server.domain.app.work_log.domain.repository.WorkLogTemplateRepository;
import com.command.toyvillage_server.domain.app.work_log.presentation.dto.response.WorkLogTemplateQueryListObjectResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class WorkLogTemplateQueryListService {
    private final WorkLogTemplateRepository workLogTemplateRepository;

    @Transactional(readOnly = true)
    public Page<WorkLogTemplateQueryListObjectResponse> execute(Pageable pageable, LocalDate createdAt) {
        Page<WorkLogTemplate> templates;

        if (createdAt == null) {
            templates = workLogTemplateRepository.findAllByDeleteYnFalse(pageable);
        } else {
            templates = workLogTemplateRepository.findAllByDeleteYnFalseAndCreatedAt(pageable, createdAt);
        }

        return templates.map(WorkLogTemplateQueryListObjectResponse::from);
    }
}

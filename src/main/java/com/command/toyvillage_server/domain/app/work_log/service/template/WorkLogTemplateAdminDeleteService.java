package com.command.toyvillage_server.domain.app.work_log.service.template;

import com.command.toyvillage_server.domain.app.work_log.domain.WorkLogTemplate;
import com.command.toyvillage_server.domain.app.work_log.domain.repository.WorkLogTemplateRepository;
import com.command.toyvillage_server.domain.app.work_log.exception.WorkLogTemplateNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WorkLogTemplateAdminDeleteService {
    private final WorkLogTemplateRepository workLogTemplateRepository;

    @Transactional
    public void execute(Long templateId) {
        WorkLogTemplate workLogTemplate = workLogTemplateRepository.findById(templateId)
            .orElseThrow(() -> WorkLogTemplateNotFoundException.EXCEPTION);

        workLogTemplate.changeDeleteYn();
    }
}

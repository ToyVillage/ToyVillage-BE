package com.command.toyvillage_server.domain.app.work_log.service.template;

import com.command.toyvillage_server.domain.app.work_log.domain.WorkLogTemplate;
import com.command.toyvillage_server.domain.app.work_log.domain.repository.WorkLogTemplateRepository;
import com.command.toyvillage_server.domain.app.work_log.exception.WorkLogTemplateNotFoundException;
import com.command.toyvillage_server.domain.app.work_log.presentation.dto.response.WorkLogTemplateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WorkLogTemplateQueryService {
    private final WorkLogTemplateRepository workLogTemplateRepository;

    @Transactional(readOnly = true)
    public WorkLogTemplateResponse execute(Long workLogTemplateId) {
        WorkLogTemplate template = workLogTemplateRepository.findByIdAndDeleteYnFalse(workLogTemplateId)
            .orElseThrow(() -> WorkLogTemplateNotFoundException.EXCEPTION);

        return WorkLogTemplateResponse.from(template);
    }
}

package com.command.toyvillage_server.domain.app.work_log.service.template;

import com.command.toyvillage_server.domain.app.work_log.domain.WorkLogTemplate;
import com.command.toyvillage_server.domain.app.work_log.domain.repository.WorkLogTemplateRepository;
import com.command.toyvillage_server.domain.app.work_log.exception.WorkLogTemplateAlreadyExistsException;
import com.command.toyvillage_server.domain.app.work_log.exception.WorkLogTemplateNotFoundException;
import com.command.toyvillage_server.domain.app.work_log.presentation.dto.request.WorkLogTemplateRequest;
import com.command.toyvillage_server.domain.app.work_log.presentation.dto.response.WorkLogTemplateCreateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkLogTemplateAdminUpdateService {
    private final WorkLogTemplateRepository workLogTemplateRepository;
    private final WorkLogTemplateAdminCreateService workLogTemplateAdminCreateService;

    @Transactional
    public WorkLogTemplateCreateResponse execute(Long templateId, WorkLogTemplateRequest request) {
        WorkLogTemplate previousTemplate = workLogTemplateRepository.findById(templateId)
            .filter(template -> !template.isDeleteYn())
            .orElseThrow(() -> WorkLogTemplateNotFoundException.EXCEPTION); // 입력받은 템플릿 진짜 있냐 확인함

        List<Long> templateIds = getTemplateIds(previousTemplate);
        if (workLogTemplateRepository.existsByTemplateTitleAndIdNotIn(request.templateTitle(), templateIds)) {
            throw WorkLogTemplateAlreadyExistsException.EXCEPTION;
        } // 해당 기존 템플릿명하고 수정한 템플릿명하고 똑같은 템플릿이 있냐 확인함

        WorkLogTemplate template = workLogTemplateAdminCreateService.createTemplate(request); // 입력받은 템플릿으로 새 템플릿 생성함
        template.setOriginalTemplateId(previousTemplate.getHistoryId()); // 기존 템플릿의 id를 원본 템플릿 필드를 저장하는 곳에 넣음
        previousTemplate.changeDeleteYn(); // 조회 되지 말라고 기존거 지운걸로 표시
        workLogTemplateRepository.flush();
        workLogTemplateRepository.save(template); // 저장

        return WorkLogTemplateCreateResponse.builder()
            .templateId(template.getId())
            .build();
    }

    private List<Long> getTemplateIds(WorkLogTemplate template) {
        Long originalTemplateId = template.getHistoryId();
        return workLogTemplateRepository.findAllByIdOrOriginalTemplateId(originalTemplateId, originalTemplateId)
            .stream()
            .map(WorkLogTemplate::getId)
            .toList();
    }
}

package com.command.toyvillage_server.domain.app.work_log.presentation.dto.response;

import com.command.toyvillage_server.domain.app.work_log.domain.WorkLogTemplate;
import lombok.Builder;
import java.util.List;

@Builder
public record WorkLogTemplateResponse(
    Long templateId,
    String templateTitle,
    List<WorkLogSectionResponse> sections,
    List<WorkLogQuestionResponse> questions
) {
    public static WorkLogTemplateResponse from(WorkLogTemplate template) {
        return WorkLogTemplateResponse.builder()
            .templateId(template.getId())
            .templateTitle(template.getTemplateTitle())
            .sections(template.getSections().stream()
                .map(WorkLogSectionResponse::from)
                .toList())
            .questions(template.getQuestions().stream()
                .map(WorkLogQuestionResponse::from)
                .toList())
            .build();
    }
}

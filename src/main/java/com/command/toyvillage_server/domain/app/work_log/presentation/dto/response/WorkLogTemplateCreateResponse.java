package com.command.toyvillage_server.domain.app.work_log.presentation.dto.response;

import com.command.toyvillage_server.domain.app.work_log.domain.WorkLogTemplate;
import lombok.Builder;

@Builder
public record WorkLogTemplateCreateResponse(
    Long templateId
) {
    public WorkLogTemplateCreateResponse from(WorkLogTemplate workLogTemplate) {
        return WorkLogTemplateCreateResponse.builder()
            .templateId(workLogTemplate.getId())
            .build();
    }
}

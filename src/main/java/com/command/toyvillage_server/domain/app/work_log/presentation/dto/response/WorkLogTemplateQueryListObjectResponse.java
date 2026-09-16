package com.command.toyvillage_server.domain.app.work_log.presentation.dto.response;

import com.command.toyvillage_server.domain.app.work_log.domain.WorkLogTemplate;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record WorkLogTemplateQueryListObjectResponse(
    Long templateId,
    String templateTitle,
    String writer,
    LocalDate createdAt
) {
    public static WorkLogTemplateQueryListObjectResponse from(WorkLogTemplate template) {
        return WorkLogTemplateQueryListObjectResponse.builder()
            .templateId(template.getId())
            .templateTitle(template.getTemplateTitle())
            .writer(template.getAppAdmin().getUsername())
            .createdAt(template.getCreatedAt())
            .build();
    }
}

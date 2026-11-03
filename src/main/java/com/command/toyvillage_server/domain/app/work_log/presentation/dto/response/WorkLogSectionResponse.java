package com.command.toyvillage_server.domain.app.work_log.presentation.dto.response;

import com.command.toyvillage_server.domain.app.work_log.domain.WorkLogSection;
import lombok.Builder;

@Builder
public record WorkLogSectionResponse(
    Long sectionId,
    String sectionName
) {
    public static WorkLogSectionResponse from(WorkLogSection section) {
        return WorkLogSectionResponse.builder()
            .sectionId(section.getId())
            .sectionName(section.getSectionName())
            .build();
    }
}

package com.command.toyvillage_server.domain.app.work_log.presentation.dto.response;

import com.command.toyvillage_server.domain.app.work_log.domain.WorkLog;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Builder
public record WorkLogDetailResponse(
    Long workLogId,
    Long templateId,
    String templateTitle,
    String writerName,
    LocalDate writeAt,
    List<WorkLogSectionAnswerResponse> sections
) {
    public static WorkLogDetailResponse of(
        WorkLog workLog,
        List<WorkLogSectionAnswerResponse> sections
    ) {
        return WorkLogDetailResponse.builder()
            .workLogId(workLog.getId())
            .templateId(workLog.getTemplate().getId())
            .templateTitle(workLog.getTemplate().getTemplateTitle())
            .writerName(workLog.getAppAdmin().getName())
            .writeAt(workLog.getWriteAt())
            .sections(sections)
            .build();
    }
}

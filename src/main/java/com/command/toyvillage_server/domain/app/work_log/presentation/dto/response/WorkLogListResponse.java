package com.command.toyvillage_server.domain.app.work_log.presentation.dto.response;

import com.command.toyvillage_server.domain.app.work_log.domain.WorkLog;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record WorkLogListResponse(
    Long workLogId,
    String writer,
    LocalDate writeAt,
    String templateTitle
) {
    public static WorkLogListResponse from(WorkLog workLog) {
        return WorkLogListResponse.builder()
            .workLogId(workLog.getId())
            .writer(workLog.getAppAdmin().getName())
            .templateTitle(workLog.getTemplate().getTemplateTitle())
            .writeAt(workLog.getWriteAt())
            .build();
    }
}

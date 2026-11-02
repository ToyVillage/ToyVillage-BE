package com.command.toyvillage_server.domain.app.all_activities.presentation.dto.response.work_log;

import com.command.toyvillage_server.domain.app.work_log.domain.WorkLog;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record WorkLogAllResponse(
        Long workLogId,
        LocalDateTime writeAt,
        boolean writeTimeKnown,
        String templateTitle,
        String title,
        String authorName,
        long fileCount
) {
    public static WorkLogAllResponse from(WorkLog workLog) {
        return WorkLogAllResponse.builder()
                .workLogId(workLog.getId())
                .writeAt(workLog.getWriteAt())
                .writeTimeKnown(workLog.isWriteTimeKnown())
                .templateTitle(workLog.getTemplate().getTemplateTitle())
                .title(workLog.getTitle())
                .authorName(workLog.getAppAdmin().getName())
                .fileCount(workLog.getAnswers().stream()
                        .filter(answer -> answer.getFile() != null)
                        .count())
                .build();
    }
}

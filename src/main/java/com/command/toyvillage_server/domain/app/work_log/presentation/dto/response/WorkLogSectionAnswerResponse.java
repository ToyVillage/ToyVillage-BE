package com.command.toyvillage_server.domain.app.work_log.presentation.dto.response;

import com.command.toyvillage_server.domain.app.work_log.domain.WorkLogSection;
import com.command.toyvillage_server.domain.app.work_log.domain.WorkLogAnswer;
import lombok.Builder;

import java.util.List;

@Builder
public record WorkLogSectionAnswerResponse(
    Long sectionId,
    String sectionName,
    List<WorkLogAnswerResponse> answers
) {
    public static WorkLogSectionAnswerResponse of(
        WorkLogSection section,
        List<WorkLogAnswer> answers
    ) {
        return WorkLogSectionAnswerResponse.builder()
            .sectionId(section.getId())
            .sectionName(section.getSectionName())
            .answers(answers.stream()
                .map(WorkLogAnswerResponse::from)
                .toList())
            .build();
    }
}

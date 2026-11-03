package com.command.toyvillage_server.domain.app.work_log.presentation.dto.response;

import com.command.toyvillage_server.domain.app.work_log.domain.WorkLogQuestionOption;
import lombok.Builder;

@Builder
public record WorkLogQuestionOptionResponse(
    Long optionId,
    Integer number,
    String content,
    boolean etcOption
) {
    public static WorkLogQuestionOptionResponse from(WorkLogQuestionOption option) {
        return WorkLogQuestionOptionResponse.builder()
            .optionId(option.getId())
            .number(option.getNumber())
            .content(option.getContent())
            .etcOption(option.isEtcOption())
            .build();
    }
}

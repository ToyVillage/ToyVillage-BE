package com.command.toyvillage_server.domain.app.work_log.presentation.dto.response;

import com.command.toyvillage_server.domain.app.work_log.domain.WorkLogAnswerOption;
import lombok.Builder;

@Builder
public record WorkLogAnswerOptionResponse(
    Long optionId,
    Integer number,
    String content,
    boolean etcOption,
    String etcText
) {
    public static WorkLogAnswerOptionResponse from(WorkLogAnswerOption answerOption) {
        return WorkLogAnswerOptionResponse.builder()
            .optionId(answerOption.getOption().getId())
            .number(answerOption.getOption().getNumber())
            .content(answerOption.getOption().getContent())
            .etcOption(answerOption.getOption().isEtcOption())
            .etcText(answerOption.getEtcText())
            .build();
    }
}

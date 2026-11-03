package com.command.toyvillage_server.domain.app.work_log.presentation.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record WorkLogAnswerOptionRequest(
    @NotNull(message = "보기를 선택해주세요.")
    Long optionId,

    @Size(max = 500, message = "기타 답변은 500자 이하여야 합니다.")
    String etcText
) {
}

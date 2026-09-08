package com.command.toyvillage_server.domain.app.work_log.presentation.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record WorkLogAnswerRequest(
    @NotNull(message = "구역을 선택해주세요.")
    Long sectionId,

    @NotNull(message = "질문을 선택해주세요.")
    Long questionId,

    @Size(max = 500, message = "답변은 500자 이하여야 합니다.")
    String answerText,

    String fileKey,

    @Valid
    List<WorkLogAnswerOptionRequest> options
) {
    public List<WorkLogAnswerOptionRequest> options() {
        if (options == null) {
            return List.of();
        }

        return options;
    }
}

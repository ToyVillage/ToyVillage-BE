package com.command.toyvillage_server.domain.app.work_log.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record WorkLogQuestionOptionRequest(
    @NotBlank(message = "보기 내용을 비워둘 수 없습니다.")
    @Size(max = 30, message = "보기 내용은 30자 이하여야 합니다.")
    String content,

    boolean etcOption
) {
}

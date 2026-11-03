package com.command.toyvillage_server.domain.app.work_log.presentation.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record WorkLogWriteRequest(
    @NotBlank(message = "업무일지 제목을 입력해주세요.")
    @Size(max = 100, message = "업무일지 제목은 100자 이하로 입력해주세요.")
    String title,

    @Valid
    @NotNull(message = "답변 목록을 전달해주세요.")
    List<WorkLogAnswerRequest> answers
) {
}

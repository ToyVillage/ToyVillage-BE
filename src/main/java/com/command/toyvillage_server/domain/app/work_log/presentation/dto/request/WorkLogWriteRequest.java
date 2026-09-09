package com.command.toyvillage_server.domain.app.work_log.presentation.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record WorkLogWriteRequest(
    @Valid
    @NotEmpty(message = "답변을 입력해주세요.")
    List<WorkLogAnswerRequest> answers
) {
}

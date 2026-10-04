package com.command.toyvillage_server.domain.app.work_log.presentation.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record WorkLogWriteRequest(
    @Valid
    @NotNull(message = "답변 목록을 전달해주세요.")
    List<WorkLogAnswerRequest> answers
) {
}

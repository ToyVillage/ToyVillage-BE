package com.command.toyvillage_server.domain.app.work_log.presentation.dto.request;

import com.command.toyvillage_server.domain.app.work_log.domain.enums.QuestionType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record WorkLogQuestionRequest(
    @NotBlank(message = "질문 내용을 비워둘 수 없습니다.")
    @Size(max = 80, message = "질문 내용은 80자 이하여야 합니다.")
    String question,

    @NotNull(message = "질문 타입을 선택해주세요.")
    QuestionType questionType,

    boolean required,

    @Valid
    List<WorkLogQuestionOptionRequest> options
) {
    public List<WorkLogQuestionOptionRequest> options() {
        if (options == null) {
            return List.of();
        }

        return options;
    }
}

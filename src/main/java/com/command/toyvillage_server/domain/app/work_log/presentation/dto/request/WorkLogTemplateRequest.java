package com.command.toyvillage_server.domain.app.work_log.presentation.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;

public record WorkLogTemplateRequest(
    @NotBlank(message = "업무일지 양식명을 비워둘 수 없습니다.")
    @Size(max = 50, message = "업무일지 양식명은 50자 이하여야 합니다.")
    String templateTitle,

    @NotEmpty(message = "구역을 하나 이상 설정해주세요.")
    List<@NotBlank(message = "구역명을 비워둘 수 없습니다.")
        @Size(max = 20, message = "구역명은 20자 이하여야 합니다.") String> sections,

    @Valid
    @NotEmpty(message = "질문을 생성해주세요.")
    List<WorkLogQuestionRequest> questions
) {
}

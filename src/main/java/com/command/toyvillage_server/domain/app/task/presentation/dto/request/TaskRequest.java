package com.command.toyvillage_server.domain.app.task.presentation.dto.request;

import com.command.toyvillage_server.domain.app.task.domain.TaskPriority;
import com.command.toyvillage_server.domain.app.task.domain.TaskVisibility;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.List;

public record TaskRequest(
        @NotBlank(message = "업무지시 제목을 입력해주세요.")
        @Size(max = 100, message = "업무지시 제목은 100자를 넘을 수 없습니다.")
        String title,

        @NotBlank(message = "업무지시 내용을 입력해주세요.")
        @Size(max = 5000, message = "업무지시 내용은 5000자를 넘을 수 없습니다.")
        String content,

        @NotEmpty(message = "업무지시 담당자를 선택해주세요.")
        List<Long> assigneeIds,

        @NotNull(message = "업무지시 완료기한을 선택해주세요.")
        LocalDate finishDate,

        @NotNull(message = "업무지시 우선순위를 선택해주세요.")
        TaskPriority priority,

        TaskVisibility visibility,

        List<String> files
) {
}

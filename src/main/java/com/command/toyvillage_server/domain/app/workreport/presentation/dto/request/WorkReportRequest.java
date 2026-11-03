package com.command.toyvillage_server.domain.app.workreport.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

public record WorkReportRequest(
        @NotBlank(message = "업무 보고 내용을 입력해주세요.")
        @Size(min = 1, max = 2000, message = "업무 보고 내용은 1자 이상 2000자 이하로 입력해주세요.")
        String content,

        @Size(min = 1, max = 2000, message = "특이사항은 1자 이상 2000자 이하로 입력해주세요.")
        String note,

        List<String> fileKey
) {
}

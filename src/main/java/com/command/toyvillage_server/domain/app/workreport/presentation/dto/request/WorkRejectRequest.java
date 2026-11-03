package com.command.toyvillage_server.domain.app.workreport.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record WorkRejectRequest(
        @NotBlank(message = "반려 사유를 입력해주세요.")
        @Size(max = 1000, message = "반려 사유는 1000자 이하로 입력해주세요.")
        String rejectionReason
) {
}

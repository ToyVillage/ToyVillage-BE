package com.command.toyvillage_server.domain.app.notification.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record FcmTokenRequest(
    @NotBlank(message = "FCM 토큰을 입력해주세요.")
    @Size(max = 512, message = "FCM 토큰은 512자 이하여야 합니다.")
    String token
) {
}

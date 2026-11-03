package com.command.toyvillage_server.global.security.jwt;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public record RefreshTokenRequest(
        @JsonProperty("refresh_token")
        @NotBlank(message = "refresh token을 비워둘 수 없습니다.")
        String refreshToken
) {
}

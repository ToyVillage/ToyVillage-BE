package com.command.toyvillage_server.domain.app.auth.admin.presentation.dto.response;

import com.command.toyvillage_server.domain.app.auth.admin.domain.AppAdmin;
import com.command.toyvillage_server.domain.web.auth.admin.presentation.dto.response.TokenResponse;
import com.fasterxml.jackson.annotation.JsonProperty;

public record AppLoginResponse(
        @JsonProperty("access_token")
        String accessToken,

        @JsonProperty("refresh_token")
        String refreshToken,

        String name,

        String role
) {
    public static AppLoginResponse of(TokenResponse tokens, AppAdmin appAdmin) {
        return new AppLoginResponse(
                tokens.accessToken(),
                tokens.refreshToken(),
                appAdmin.getName(),
                appAdmin.getRole().name()
        );
    }
}

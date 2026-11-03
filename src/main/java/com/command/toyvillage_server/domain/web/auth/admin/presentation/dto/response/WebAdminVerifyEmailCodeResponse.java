package com.command.toyvillage_server.domain.web.auth.admin.presentation.dto.response;

public record WebAdminVerifyEmailCodeResponse(
        String token
) {
    public static WebAdminVerifyEmailCodeResponse of(String token) {
        return new WebAdminVerifyEmailCodeResponse(token);
    }
}

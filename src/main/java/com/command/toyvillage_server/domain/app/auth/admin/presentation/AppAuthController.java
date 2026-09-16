package com.command.toyvillage_server.domain.app.auth.admin.presentation;

import com.command.toyvillage_server.domain.app.auth.admin.presentation.dto.request.AppChangePasswordRequest;
import com.command.toyvillage_server.domain.app.auth.admin.presentation.dto.request.AppLoginRequest;
import com.command.toyvillage_server.domain.app.auth.admin.presentation.dto.response.AppLoginResponse;
import com.command.toyvillage_server.domain.app.auth.admin.service.AppChangePasswordService;
import com.command.toyvillage_server.domain.app.auth.admin.service.AppLoginService;
import com.command.toyvillage_server.domain.app.auth.admin.service.AppLogoutService;
import com.command.toyvillage_server.domain.app.auth.admin.service.AppReissueService;
import com.command.toyvillage_server.domain.web.auth.admin.presentation.dto.response.TokenResponse;
import com.command.toyvillage_server.global.common.response.MessageResponse;
import com.command.toyvillage_server.global.security.jwt.RefreshTokenRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app/auth")
@RequiredArgsConstructor
public class AppAuthController {
    private final AppLoginService appLoginService;
    private final AppLogoutService appLogoutService;
    private final AppChangePasswordService appChangePasswordService;
    private final AppReissueService appReissueService;

    @PostMapping("/login")
    public ResponseEntity<AppLoginResponse> login(@RequestBody @Valid AppLoginRequest request) {
        return ResponseEntity.ok(appLoginService.execute(request));
    }

    @PostMapping("/logout")
    public ResponseEntity<MessageResponse> logout(Authentication authentication) {
        appLogoutService.execute(authentication.getName());
        return ResponseEntity.ok(MessageResponse.of("로그아웃되었습니다."));
    }

    @PatchMapping("/password")
    public ResponseEntity<MessageResponse> changePassword(
            @RequestBody @Valid AppChangePasswordRequest request
    ) {
        appChangePasswordService.execute(request);
        return ResponseEntity.ok(MessageResponse.of("비밀번호가 변경되었습니다."));
    }

    @PostMapping("/reissue")
    public ResponseEntity<TokenResponse> reissue(@RequestBody @Valid RefreshTokenRequest request) {
        return ResponseEntity.ok(appReissueService.execute(request.refreshToken()));
    }
}

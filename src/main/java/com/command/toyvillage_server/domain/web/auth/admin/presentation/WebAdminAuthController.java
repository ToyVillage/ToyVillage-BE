package com.command.toyvillage_server.domain.web.auth.admin.presentation;

import com.command.toyvillage_server.domain.web.auth.admin.presentation.dto.request.*;
import com.command.toyvillage_server.domain.web.auth.admin.presentation.dto.response.TokenResponse;
import com.command.toyvillage_server.domain.web.auth.admin.presentation.dto.response.WebAdminVerifyEmailCodeResponse;
import com.command.toyvillage_server.domain.web.auth.admin.service.WebAdminChangePasswordService;
import com.command.toyvillage_server.domain.web.auth.admin.service.WebAdminLoginService;
import com.command.toyvillage_server.domain.web.auth.admin.service.WebAdminReissueService;
import com.command.toyvillage_server.domain.web.auth.admin.service.WebAdminSendVerificationCodeService;
import com.command.toyvillage_server.domain.web.auth.admin.service.WebAdminSignUpService;
import com.command.toyvillage_server.domain.web.auth.admin.service.WebAdminVerifyEmailCodeService;
import com.command.toyvillage_server.global.common.response.MessageResponse;
import com.command.toyvillage_server.global.security.jwt.RefreshTokenRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/web/auth")
@RequiredArgsConstructor
public class WebAdminAuthController {
    private final WebAdminLoginService webAdminLoginService;
    private final WebAdminSignUpService webAdminSignUpService;
    private final WebAdminReissueService webAdminReissueService;
    private final WebAdminSendVerificationCodeService webAdminSendVerificationCodeService;
    private final WebAdminChangePasswordService webAdminChangePasswordService;
    private final WebAdminVerifyEmailCodeService webAdminVerifyEmailCodeService;

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody @Valid WebAdminLoginRequest request) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(webAdminLoginService.execute(request));
    }

    @PostMapping("/signup")
    public ResponseEntity<MessageResponse> signup(
            @RequestBody @Valid WebAdminSignUpRequest request
    ){
        webAdminSignUpService.execute(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(MessageResponse.of("회원가입 완료되었습니다. 로그인 후 이용해주세요."));
    }

    @PostMapping("/reissue")
    public ResponseEntity<TokenResponse> reissue(@RequestBody @Valid RefreshTokenRequest request) {
        return ResponseEntity.ok(webAdminReissueService.execute(request.refreshToken()));
    }

    @PostMapping("/password/verification")
    public ResponseEntity<MessageResponse> sendVerificationEmail(
            @RequestBody @Valid WebAdminPasswordResetRequest request
    ){
        webAdminSendVerificationCodeService.execute(request.email());

        return ResponseEntity.ok(
                MessageResponse.of("해당 이메일로 인증번호가 발송되었습니다, 인증코드를 입력해주세요.")
        );
    }

    @PostMapping("/password/verification/confirm")
    public ResponseEntity<WebAdminVerifyEmailCodeResponse> verifyEmailCode(
            @RequestBody @Valid WebAdminVerifyEmailCodeRequest request
    ){
        String resetToken = webAdminVerifyEmailCodeService.execute(request.email(), request.code());

        return ResponseEntity.ok(WebAdminVerifyEmailCodeResponse.of(resetToken));
    }

    @PatchMapping("/password")
    public ResponseEntity<MessageResponse> changePassword(
            @RequestBody @Valid WebAdminChangePasswordRequest request
    ){
        webAdminChangePasswordService.execute(request);

        return ResponseEntity.ok(
                MessageResponse.of("비밀번호가 변경되었습니다.")
        );
    }
}

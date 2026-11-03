package com.command.toyvillage_server.domain.web.auth.admin.service;

import com.command.toyvillage_server.domain.web.auth.admin.domain.EmailVerification;
import com.command.toyvillage_server.domain.web.auth.admin.domain.PasswordResetToken;
import com.command.toyvillage_server.domain.web.auth.admin.domain.repository.EmailVerificationRepository;
import com.command.toyvillage_server.domain.web.auth.admin.domain.repository.PasswordResetTokenRepository;
import com.command.toyvillage_server.domain.web.auth.admin.exception.ManyRequestException;
import com.command.toyvillage_server.domain.web.auth.admin.exception.VerificationCodeExpiredException;
import com.command.toyvillage_server.domain.web.auth.admin.exception.VerificationCodeNotMatchedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WebAdminVerifyEmailCodeService {

    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final EmailVerificationRepository emailVerificationRepository;

    @Transactional
    public String execute(String email, String code){
        EmailVerification verification = emailVerificationRepository.findById(email)
                .orElseThrow(() -> VerificationCodeExpiredException.EXCEPTION);

        if(verification.getFailCount() == 0){
            throw ManyRequestException.EXCEPTION;
        }

        if (!verification.isValid(code)){
            verification.increaseFailCount();
            throw VerificationCodeNotMatchedException.EXCEPTION;
        }

        emailVerificationRepository.delete(verification);

        PasswordResetToken resetToken = PasswordResetToken.create(email);
        passwordResetTokenRepository.save(resetToken);

        return resetToken.getToken();
    }
}

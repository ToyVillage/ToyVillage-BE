package com.command.toyvillage_server.domain.web.auth.admin.service;

import com.command.toyvillage_server.domain.web.auth.admin.domain.WebAdmin;
import com.command.toyvillage_server.domain.web.auth.admin.domain.PasswordResetToken;
import com.command.toyvillage_server.domain.web.auth.admin.domain.repository.WebAdminRepository;
import com.command.toyvillage_server.domain.web.auth.admin.domain.repository.PasswordResetTokenRepository;
import com.command.toyvillage_server.domain.web.auth.admin.exception.WebAdminNotFoundException;
import com.command.toyvillage_server.domain.web.auth.admin.exception.ResetTokenNotFoundException;
import com.command.toyvillage_server.domain.web.auth.admin.presentation.dto.request.WebAdminChangePasswordRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WebAdminChangePasswordService {

    private final WebAdminRepository webAdminRepository;
    private final PasswordEncoder passwordEncoder;
    private final PasswordResetTokenRepository passwordResetTokenRepository;

    @Transactional
    public void execute(WebAdminChangePasswordRequest request){
        PasswordResetToken resetToken = passwordResetTokenRepository.findById(request.resetToken())
                .orElseThrow(() -> ResetTokenNotFoundException.EXCEPTION);

        WebAdmin webAdmin = webAdminRepository.findByEmail(resetToken.getEmail())
                .orElseThrow(() -> WebAdminNotFoundException.EXCEPTION);

        webAdmin.changePassword(passwordEncoder.encode(request.newPassword()));

        passwordResetTokenRepository.delete(resetToken);
    }
}

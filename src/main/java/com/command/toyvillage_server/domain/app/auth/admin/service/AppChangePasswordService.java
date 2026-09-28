package com.command.toyvillage_server.domain.app.auth.admin.service;

import com.command.toyvillage_server.domain.app.auth.admin.domain.AppAdmin;
import com.command.toyvillage_server.domain.app.auth.admin.domain.repository.AppAdminRepository;
import com.command.toyvillage_server.domain.app.auth.admin.exception.AppAdminNotFoundException;
import com.command.toyvillage_server.domain.app.auth.admin.presentation.dto.request.AppChangePasswordRequest;
import com.command.toyvillage_server.domain.web.auth.admin.domain.RefreshToken;
import com.command.toyvillage_server.domain.web.auth.admin.domain.repository.RefreshTokenRepository;
import com.command.toyvillage_server.global.error.exception.ErrorCode;
import com.command.toyvillage_server.global.error.exception.ToyVillageException;
import com.command.toyvillage_server.global.security.auth.AppAdminDetails;
import com.command.toyvillage_server.global.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AppChangePasswordService {
    private final AppAdminRepository appAdminRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public void execute(AppChangePasswordRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !(authentication.getPrincipal() instanceof AppAdminDetails details)) {
            throw AppAdminNotFoundException.EXCEPTION;
        }

        AppAdmin appAdmin = appAdminRepository.findByIdAndDeleteStatusFalse(details.getId())
                .orElseThrow(() -> AppAdminNotFoundException.EXCEPTION);
        
        if (!passwordEncoder.matches(request.currentPassword(), appAdmin.getPassword())) {
            throw new ToyVillageException(ErrorCode.PASSWORD_MISMATCH);
        }

        appAdmin.changePassword(passwordEncoder.encode(request.newPassword()));
        appAdminRepository.saveAndFlush(appAdmin);

        String refreshTokenKey = jwtTokenProvider.getAppRefreshTokenKey(appAdmin.getUsername());
        refreshTokenRepository.deleteById(refreshTokenKey);
    }
}

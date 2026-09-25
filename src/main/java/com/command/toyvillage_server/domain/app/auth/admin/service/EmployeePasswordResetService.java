package com.command.toyvillage_server.domain.app.auth.admin.service;

import com.command.toyvillage_server.domain.app.auth.admin.domain.AppAdmin;
import com.command.toyvillage_server.domain.app.auth.admin.domain.AppAdminRole;
import com.command.toyvillage_server.domain.app.auth.admin.domain.repository.AppAdminRepository;
import com.command.toyvillage_server.domain.app.auth.admin.exception.AppAdminNotFoundException;
import com.command.toyvillage_server.domain.web.auth.admin.domain.repository.RefreshTokenRepository;
import com.command.toyvillage_server.global.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EmployeePasswordResetService {
    private final AppAdminRepository appAdminRepository;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public void execute(Long appAdminId) {
        AppAdmin appAdmin = appAdminRepository.findByIdAndRoleAndDeleteStatusFalse(appAdminId, AppAdminRole.EMPLOYEE)
            .orElseThrow(() -> AppAdminNotFoundException.EXCEPTION);

        appAdmin.changePassword(passwordEncoder.encode(appAdmin.getUsername()));
        appAdminRepository.saveAndFlush(appAdmin);

        refreshTokenRepository.deleteById(jwtTokenProvider.getAppRefreshTokenKey(appAdmin.getUsername()));
    }
}

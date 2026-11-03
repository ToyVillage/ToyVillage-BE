package com.command.toyvillage_server.domain.app.auth.admin.service;

import com.command.toyvillage_server.domain.app.auth.admin.domain.AppAdmin;
import com.command.toyvillage_server.domain.app.auth.admin.domain.repository.AppAdminRepository;
import com.command.toyvillage_server.domain.app.auth.admin.presentation.dto.request.AppLoginRequest;
import com.command.toyvillage_server.domain.app.auth.admin.presentation.dto.response.AppLoginResponse;
import com.command.toyvillage_server.domain.web.auth.admin.exception.LoginInfoNotMatchedException;
import com.command.toyvillage_server.domain.web.auth.admin.presentation.dto.response.TokenResponse;
import com.command.toyvillage_server.global.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AppLoginService {
    private final AppAdminRepository appAdminRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public AppLoginResponse execute(AppLoginRequest request) {
        AppAdmin appAdmin = appAdminRepository.findByUsernameAndDeleteStatusFalse(request.username())
                .orElseThrow(() -> LoginInfoNotMatchedException.EXCEPTION);

        if (!passwordEncoder.matches(request.password(), appAdmin.getPassword())) {
            throw LoginInfoNotMatchedException.EXCEPTION;
        }

        TokenResponse tokens = jwtTokenProvider.receiveAppToken(appAdmin.getUsername());
        return AppLoginResponse.of(tokens, appAdmin);
    }
}

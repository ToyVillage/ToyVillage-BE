package com.command.toyvillage_server.domain.app.auth.admin.service;

import com.command.toyvillage_server.domain.web.auth.admin.domain.repository.RefreshTokenRepository;
import com.command.toyvillage_server.global.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppLogoutService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public void execute(String username) {
        refreshTokenRepository.deleteById(jwtTokenProvider.getAppRefreshTokenKey(username));
    }
}

package com.command.toyvillage_server.domain.web.auth.admin.service;

import com.command.toyvillage_server.domain.web.auth.admin.domain.RefreshToken;
import com.command.toyvillage_server.domain.web.auth.admin.domain.repository.RefreshTokenRepository;
import com.command.toyvillage_server.domain.web.auth.admin.exception.RefreshTokenNotFoundException;
import com.command.toyvillage_server.domain.web.auth.admin.presentation.dto.response.TokenResponse;
import com.command.toyvillage_server.global.security.jwt.JwtTokenProvider;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WebAdminReissueService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public TokenResponse execute(String token) {
        Claims claims = jwtTokenProvider.getWebRefreshClaims(token);
        String tokenKey = jwtTokenProvider.getWebRefreshTokenKey(claims.getSubject());

        RefreshToken refreshToken = refreshTokenRepository.findById(tokenKey)
                .filter(savedToken -> savedToken.getToken().equals(token))
                .orElseThrow(() -> RefreshTokenNotFoundException.EXCEPTION);

        refreshTokenRepository.deleteById(tokenKey);
        return jwtTokenProvider.receiveWebToken(claims.getSubject());
    }
}

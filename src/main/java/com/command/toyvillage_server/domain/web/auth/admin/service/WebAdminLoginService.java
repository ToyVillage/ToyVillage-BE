package com.command.toyvillage_server.domain.web.auth.admin.service;

import com.command.toyvillage_server.domain.web.auth.admin.domain.WebAdmin;
import com.command.toyvillage_server.domain.web.auth.admin.domain.repository.WebAdminRepository;
import com.command.toyvillage_server.domain.web.auth.admin.exception.LoginInfoNotMatchedException;
import com.command.toyvillage_server.domain.web.auth.admin.presentation.dto.request.WebAdminLoginRequest;
import com.command.toyvillage_server.domain.web.auth.admin.presentation.dto.response.TokenResponse;
import com.command.toyvillage_server.global.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class WebAdminLoginService {
    private final JwtTokenProvider jwtTokenProvider;
    private final WebAdminRepository webAdminRepository;
    private final PasswordEncoder passwordEncoder;

    public TokenResponse execute(WebAdminLoginRequest request){
        log.info("웹 관리자 로그인 시도 / email : {}", request.email());

        WebAdmin webAdmin = webAdminRepository.findByEmail(request.email())
                .orElseThrow(() -> LoginInfoNotMatchedException.EXCEPTION);

        if(!passwordEncoder.matches(request.password(), webAdmin.getPassword())) {
            throw LoginInfoNotMatchedException.EXCEPTION;
        }

        log.info("웹 관리자 로그인 성공 / email : {}", request.email());
        return jwtTokenProvider.receiveWebToken(webAdmin.getEmail());
    }
}

package com.command.toyvillage_server.domain.web.auth.admin.service;

import com.command.toyvillage_server.domain.web.auth.admin.domain.WebAdmin;
import com.command.toyvillage_server.domain.web.auth.admin.domain.repository.WebAdminRepository;
import com.command.toyvillage_server.domain.web.auth.admin.exception.WebAdminAlreadyExistsException;
import com.command.toyvillage_server.domain.web.auth.admin.presentation.dto.request.WebAdminSignUpRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WebAdminSignUpService {
    private final WebAdminRepository webAdminRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void execute(WebAdminSignUpRequest request) {
        if(webAdminRepository.existsByEmail(request.email())){
            throw WebAdminAlreadyExistsException.EXCEPTION;
        }

        String password = passwordEncoder.encode(request.password());

        WebAdmin webAdmin = WebAdmin.create(
                request.email(),
                password
        );

        webAdminRepository.save(webAdmin);
    }
}

package com.command.toyvillage_server.domain.app.notification.service;

import com.command.toyvillage_server.domain.app.auth.admin.domain.repository.AppAdminRepository;
import com.command.toyvillage_server.domain.app.auth.admin.exception.AppAdminNotFoundException;
import com.command.toyvillage_server.domain.app.auth.admin.facade.UserFacade;
import com.command.toyvillage_server.domain.app.notification.domain.repository.FcmTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FcmTokenCreateService {
    private final FcmTokenRepository fcmTokenRepository;
    private final AppAdminRepository appAdminRepository;
    private final UserFacade userFacade;

    @Transactional
    public void execute(String token) {
        Long appAdminId = userFacade.getCurrentUserId();
        if (!appAdminRepository.existsByIdAndDeleteStatusFalse(appAdminId)) {
            throw AppAdminNotFoundException.EXCEPTION;
        }
        fcmTokenRepository.register(token, appAdminId);
    }
}

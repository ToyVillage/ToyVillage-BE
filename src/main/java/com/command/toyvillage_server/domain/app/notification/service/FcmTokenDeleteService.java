package com.command.toyvillage_server.domain.app.notification.service;

import com.command.toyvillage_server.domain.app.auth.admin.facade.UserFacade;
import com.command.toyvillage_server.domain.app.notification.domain.repository.FcmTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FcmTokenDeleteService {
    private final FcmTokenRepository fcmTokenRepository;
    private final UserFacade userFacade;

    @Transactional
    public void execute(String token) {
        fcmTokenRepository.deleteByTokenAndAppAdmin_Id(token, userFacade.getCurrentUserId());
    }
}

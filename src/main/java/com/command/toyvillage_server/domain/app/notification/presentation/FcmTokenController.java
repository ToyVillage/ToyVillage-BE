package com.command.toyvillage_server.domain.app.notification.presentation;

import com.command.toyvillage_server.domain.app.notification.presentation.dto.request.FcmTokenRequest;
import com.command.toyvillage_server.domain.app.notification.service.FcmTokenCreateService;
import com.command.toyvillage_server.domain.app.notification.service.FcmTokenDeleteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/app/fcm/token")
public class FcmTokenController {
    private final FcmTokenCreateService fcmTokenCreateService;
    private final FcmTokenDeleteService fcmTokenDeleteService;

    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void register(@RequestBody @Valid FcmTokenRequest request) {
        fcmTokenCreateService.execute(request.token());
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@RequestBody @Valid FcmTokenRequest request) {
        fcmTokenDeleteService.execute(request.token());
    }
}

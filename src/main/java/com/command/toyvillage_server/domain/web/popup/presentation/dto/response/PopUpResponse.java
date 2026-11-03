package com.command.toyvillage_server.domain.web.popup.presentation.dto.response;

import com.command.toyvillage_server.domain.web.popup.domain.PopUp;

import java.time.LocalDate;

public record PopUpResponse(
        Long id,
        String popupImage,
        LocalDate expirationDate,
        int priority
) {
    public static PopUpResponse from(PopUp popUp) {
        return new PopUpResponse(
                popUp.getId(),
                popUp.getFile().getFileKey(),
                popUp.getExpirationDate(),
                popUp.getPriority()
        );
    }
}

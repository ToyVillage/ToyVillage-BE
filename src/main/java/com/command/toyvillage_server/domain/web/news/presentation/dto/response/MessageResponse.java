package com.command.toyvillage_server.domain.web.news.presentation.dto.response;


public record MessageResponse(String message) {

    public static MessageResponse of(String message) {
        return new MessageResponse(message);
    }
}

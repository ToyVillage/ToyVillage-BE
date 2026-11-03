package com.command.toyvillage_server.global.error;

import com.command.toyvillage_server.global.error.exception.ErrorCode;

import java.time.LocalDateTime;

public record ErrorResponse(
        String message,
        Integer status,
        LocalDateTime timestamp,
        String description
) {
    public static ErrorResponse of(ErrorCode errorCode, String description) {
        return new ErrorResponse(
                errorCode.getErrorMessage(),
                errorCode.getStatusCode(),
                LocalDateTime.now(),
                description
        );
    }

    public static ErrorResponse of(int statusCode, String description) {
        return new ErrorResponse(
                description,
                statusCode,
                LocalDateTime.now(),
                description
        );
    }
}

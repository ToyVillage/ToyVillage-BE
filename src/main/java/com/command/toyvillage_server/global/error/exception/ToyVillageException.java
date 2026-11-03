package com.command.toyvillage_server.global.error.exception;

import lombok.Getter;

@Getter
public class ToyVillageException extends RuntimeException {
    private final ErrorCode errorCode;
    public ToyVillageException(ErrorCode errorCode) {
        super(errorCode.getErrorMessage());
        this.errorCode = errorCode;
    }
    public ToyVillageException(ErrorCode errorCode, String customMessage) {
        super(customMessage);
        this.errorCode = errorCode;
    }
}

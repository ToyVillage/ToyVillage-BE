package com.command.toyvillage_server.domain.app.notice.exception;

import com.command.toyvillage_server.global.error.exception.ErrorCode;
import com.command.toyvillage_server.global.error.exception.ToyVillageException;

public class NoticeFcmNotSendException extends ToyVillageException {
    public static final ToyVillageException EXCEPTION = new NoticeFcmNotSendException();

    public NoticeFcmNotSendException() {
        super(ErrorCode.NOTICE_FCM_NOT_SEND);
    }
}

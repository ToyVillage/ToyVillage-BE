package com.command.toyvillage_server.domain.app.notice.exception;

import com.command.toyvillage_server.global.error.exception.ErrorCode;
import com.command.toyvillage_server.global.error.exception.ToyVillageException;

public class NoticeNotFoundException extends ToyVillageException {
    public static final ToyVillageException EXCEPTION = new NoticeNotFoundException();

    public NoticeNotFoundException() {
        super(ErrorCode.NOTICE_NOT_FOUND);
    }
}

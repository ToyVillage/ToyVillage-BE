package com.command.toyvillage_server.domain.app.open_time.exception;

import com.command.toyvillage_server.global.error.exception.ErrorCode;
import com.command.toyvillage_server.global.error.exception.ToyVillageException;

public class OpenTimeNotFoundException extends ToyVillageException {
    public static final ToyVillageException EXCEPTION = new OpenTimeNotFoundException();

    public OpenTimeNotFoundException() {
        super(ErrorCode.OPEN_TIME_NOT_FOUND);
    }
}

package com.command.toyvillage_server.domain.app.close_day.exception;

import com.command.toyvillage_server.global.error.exception.ErrorCode;
import com.command.toyvillage_server.global.error.exception.ToyVillageException;

public class CloseDayNotFoundException extends ToyVillageException {
    public static final ToyVillageException EXCEPTION = new CloseDayNotFoundException();

    public CloseDayNotFoundException() {
        super(ErrorCode.CLOSE_DAY_NOT_FOUND);
    }
}

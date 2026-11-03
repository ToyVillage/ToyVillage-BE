package com.command.toyvillage_server.domain.app.open_time.exception;

import com.command.toyvillage_server.global.error.exception.ErrorCode;
import com.command.toyvillage_server.global.error.exception.ToyVillageException;

public class OpenTimeInvalidPeriodException extends ToyVillageException {
    public static final ToyVillageException EXCEPTION = new OpenTimeInvalidPeriodException();

    public OpenTimeInvalidPeriodException() {
        super(ErrorCode.OPEN_TIME_INVALID_PERIOD);
    }
}

package com.command.toyvillage_server.domain.app.close_day.exception;

import com.command.toyvillage_server.global.error.exception.ErrorCode;
import com.command.toyvillage_server.global.error.exception.ToyVillageException;

public class CloseDayInvalidPeriodException extends ToyVillageException {
    public static final ToyVillageException EXCEPTION = new CloseDayInvalidPeriodException();

    public CloseDayInvalidPeriodException() {
        super(ErrorCode.CLOSE_DAY_INVALID_PERIOD);
    }
}

package com.command.toyvillage_server.domain.app.reservation.exception;

import com.command.toyvillage_server.global.error.exception.ErrorCode;
import com.command.toyvillage_server.global.error.exception.ToyVillageException;

public class ReservationInvalidTimeException extends ToyVillageException {
    public static final ToyVillageException EXCEPTION = new ReservationInvalidTimeException();

    private ReservationInvalidTimeException() {
        super(ErrorCode.RESERVATION_INVALID_TIME);
    }
}

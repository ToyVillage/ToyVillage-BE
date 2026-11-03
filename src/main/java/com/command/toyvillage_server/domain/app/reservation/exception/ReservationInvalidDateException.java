package com.command.toyvillage_server.domain.app.reservation.exception;

import com.command.toyvillage_server.global.error.exception.ErrorCode;
import com.command.toyvillage_server.global.error.exception.ToyVillageException;

public class ReservationInvalidDateException extends ToyVillageException {
    public static final ToyVillageException EXCEPTION = new ReservationInvalidDateException();

    private ReservationInvalidDateException() {
        super(ErrorCode.RESERVATION_INVALID_DATE);
    }
}

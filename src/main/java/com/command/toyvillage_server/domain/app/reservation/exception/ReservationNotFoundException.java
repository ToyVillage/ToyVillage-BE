package com.command.toyvillage_server.domain.app.reservation.exception;

import com.command.toyvillage_server.domain.app.notice.exception.NoticeNotFoundException;
import com.command.toyvillage_server.global.error.exception.ErrorCode;
import com.command.toyvillage_server.global.error.exception.ToyVillageException;

public class ReservationNotFoundException extends ToyVillageException {
    public static final ToyVillageException EXCEPTION = new ReservationNotFoundException();

    public ReservationNotFoundException() {
        super(ErrorCode.RESERVATION_NOT_FOUND);
    }
}

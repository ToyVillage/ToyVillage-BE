package com.command.toyvillage_server.domain.app.reservation.exception;

import com.command.toyvillage_server.global.error.exception.ErrorCode;
import com.command.toyvillage_server.global.error.exception.ToyVillageException;

public class ReservationPermissionNotFoundException extends ToyVillageException {
    public static final ReservationPermissionNotFoundException EXCEPTION =
        new ReservationPermissionNotFoundException();

    private ReservationPermissionNotFoundException() {
        super(ErrorCode.RESERVATION_PERMISSION_NOT_FOUND);
    }
}

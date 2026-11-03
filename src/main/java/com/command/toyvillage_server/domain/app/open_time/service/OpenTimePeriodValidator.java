package com.command.toyvillage_server.domain.app.open_time.service;

import com.command.toyvillage_server.domain.app.open_time.exception.OpenTimeInvalidPeriodException;

import java.time.LocalTime;

final class OpenTimePeriodValidator {

    private OpenTimePeriodValidator() {
    }

    static void validate(LocalTime startOpenTime, LocalTime endOpenTime) {
        if (endOpenTime.isBefore(startOpenTime)) {
            throw OpenTimeInvalidPeriodException.EXCEPTION;
        }
    }
}

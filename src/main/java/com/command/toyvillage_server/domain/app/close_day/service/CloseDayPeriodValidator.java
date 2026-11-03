package com.command.toyvillage_server.domain.app.close_day.service;

import com.command.toyvillage_server.domain.app.close_day.exception.CloseDayInvalidPeriodException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
final class CloseDayPeriodValidator {
    static void validate(LocalDate startCloseTime, LocalDate endCloseTime) {
        if (endCloseTime.isBefore(startCloseTime)) {
            throw CloseDayInvalidPeriodException.EXCEPTION;
        }
    }
}

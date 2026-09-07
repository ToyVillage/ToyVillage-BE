package com.command.toyvillage_server.domain.app.reservation.domain.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@RequiredArgsConstructor
public enum ReservationStatus {
    BEFORE_SITE_VISIT("사전답사 전"),
    SITE_VISIT_COMPLETED("사전답사 완료"),
    VISIT_COMPLETED("방문 완료");

    private final String description;

    public static ReservationStatus from(
        LocalDate visitSiteDate,
        LocalDate visitDate,
        LocalDate today
    ) {
        if (visitDate.isBefore(today)) {
            return VISIT_COMPLETED;
        }

        if (visitSiteDate.isBefore(today)) {
            return SITE_VISIT_COMPLETED;
        }

        return BEFORE_SITE_VISIT;
    }

    @JsonValue
    public String getDescription() {
        return description;
    }
}

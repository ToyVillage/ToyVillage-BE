package com.command.toyvillage_server.domain.app.reservation.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;

@Getter
@RequiredArgsConstructor
public enum ReservationSortType {
    COUNSEL_DATE("상담일순", "counselDate"),
    RESERVATION_DATE("예약일순", "reservationDate");

    private final String description;
    private final String property;

    public Sort toSort() {
        return Sort.by(Sort.Direction.DESC, property).and(Sort.by(Sort.Direction.DESC, "id"));
    }
}

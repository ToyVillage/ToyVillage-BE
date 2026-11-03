package com.command.toyvillage_server.domain.app.reservation.presentation.dto.response;

import com.command.toyvillage_server.domain.app.reservation.domain.Reservation;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Builder
public record ReservationListResponse(
    Long id,
    String title,
    String reservationName,
    LocalDate visitDate,
    LocalTime visitTime,
    Integer reservationCount
) {
    public static ReservationListResponse from(Reservation reservation) {
        return ReservationListResponse.builder()
            .id(reservation.getId())
            .title(reservation.getTitle())
            .reservationName(reservation.getReservationName())
            .visitDate(reservation.getVisitDate())
            .visitTime(reservation.getVisitTime())
            .reservationCount(reservation.getReservationCount())
            .build();
    }
}

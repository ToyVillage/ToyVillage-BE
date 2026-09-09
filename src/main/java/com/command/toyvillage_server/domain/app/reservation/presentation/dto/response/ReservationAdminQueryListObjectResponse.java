package com.command.toyvillage_server.domain.app.reservation.presentation.dto.response;

import com.command.toyvillage_server.domain.app.reservation.domain.Reservation;
import com.command.toyvillage_server.domain.app.reservation.domain.enums.ReservationStatus;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalTime;

@Builder
public record ReservationAdminQueryListObjectResponse(
    Long id,
    String title,
    LocalDate counselDate,
    LocalDate reservationDate,
    LocalTime reservationTime,
    String location,
    int count,
    ReservationStatus status
) {
    public static ReservationAdminQueryListObjectResponse from(
        Reservation reservation
    ) {
        return ReservationAdminQueryListObjectResponse.builder()
            .id(reservation.getId())
            .title(reservation.getTitle())
            .counselDate(reservation.getCounselDate())
            .reservationDate(reservation.getReservationDate())
            .reservationTime(reservation.getReservationTime())
            .location(reservation.getLocation())
            .count(reservation.getReservationCount())
            .status(reservation.getStatus())
            .build();
    }
}

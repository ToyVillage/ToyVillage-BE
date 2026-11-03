package com.command.toyvillage_server.domain.app.reservation.presentation.dto.response;

import com.command.toyvillage_server.domain.app.reservation.domain.Reservation;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalTime;

@Builder
public record ReservationResponse(
    Long id,
    String title,
    String reservationName,
    Integer reservationCount,
    LocalDate reservationDate,
    LocalTime reservationTime,
    LocalDate visitSiteDate,
    LocalTime visitSiteTime,
    LocalTime visitSiteExitTime,
    Integer visitSiteCount,
    LocalDate visitDate,
    LocalTime visitTime,
    LocalTime exitTime,
    Integer leaderCount,
    String location,
    Integer money
) {
    public static ReservationResponse from(Reservation reservation) {
        return ReservationResponse.builder()
            .id(reservation.getId())
            .title(reservation.getTitle())
            .reservationName(reservation.getReservationName())
            .reservationCount(reservation.getReservationCount())
            .reservationDate(reservation.getReservationDate())
            .reservationTime(reservation.getReservationTime())
            .visitSiteDate(reservation.getVisitSiteDate())
            .visitSiteTime(reservation.getVisitSiteTime())
            .visitSiteExitTime(reservation.getVisitSiteExitTime())
            .visitSiteCount(reservation.getVisitSiteCount())
            .visitDate(reservation.getVisitDate())
            .visitTime(reservation.getVisitTime())
            .exitTime(reservation.getExitTime())
            .leaderCount(reservation.getLeaderCount())
            .location(reservation.getLocation())
            .money(reservation.getMoney())
            .build();
    }
}

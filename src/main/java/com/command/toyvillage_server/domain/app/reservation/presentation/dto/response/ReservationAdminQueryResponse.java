package com.command.toyvillage_server.domain.app.reservation.presentation.dto.response;

import com.command.toyvillage_server.domain.app.reservation.domain.Reservation;
import com.command.toyvillage_server.domain.app.reservation.domain.enums.ReservationStatus;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalTime;

@Builder
public record ReservationAdminQueryResponse(
    Long id,
    LocalDate counselDate,
    LocalDate visitDate,
    LocalTime visitTime,
    LocalTime exitTime,
    String reservationName,
    int reservationCount,
    String location,
    String title,
    int money,
    ReservationStatus status,
    int leaderCount,
    String leaderPhoneNumber,
    int visitSiteCount,
    LocalDate visitSiteDate,
    LocalTime visitSiteTime,
    LocalTime visitSiteExitTime
) {
    public static ReservationAdminQueryResponse from(Reservation reservation) {
        return ReservationAdminQueryResponse.builder()
            .id(reservation.getId())
            .counselDate(reservation.getCounselDate())
            .visitDate(reservation.getVisitDate())
            .visitTime(reservation.getVisitTime())
            .exitTime(reservation.getExitTime())
            .reservationName(reservation.getReservationName())
            .reservationCount(reservation.getReservationCount())
            .location(reservation.getLocation())
            .title(reservation.getTitle())
            .money(reservation.getMoney())
            .status(reservation.getStatus())
            .leaderCount(reservation.getLeaderCount())
            .leaderPhoneNumber(reservation.getLeaderPhoneNumber())
            .visitSiteCount(reservation.getVisitSiteCount())
            .visitSiteDate(reservation.getVisitSiteDate())
            .visitSiteTime(reservation.getVisitSiteTime())
            .visitSiteExitTime(reservation.getVisitSiteExitTime())
            .build();
    }
}

package com.command.toyvillage_server.domain.app.reservation.presentation.dto.response;

import lombok.Builder;
import org.springframework.data.domain.Page;

@Builder
public record ReservationAdminQueryListResponse(
    int beforeVisitSite,
    int doneVisitSite,
    int doneVisit,
    Page<ReservationAdminQueryListObjectResponse> reservationAdminQueryListObjectResponse
    ) {
    public static ReservationAdminQueryListResponse of(
        int beforeVisitSite,
        int doneVisitSite,
        int doneVisit,
        Page<ReservationAdminQueryListObjectResponse> reservationAdminQueryListObjectResponse
    ) {
        return ReservationAdminQueryListResponse.builder()
            .beforeVisitSite(beforeVisitSite)
            .doneVisitSite(doneVisitSite)
            .doneVisit(doneVisit)
            .reservationAdminQueryListObjectResponse(reservationAdminQueryListObjectResponse)
            .build();
    }
}

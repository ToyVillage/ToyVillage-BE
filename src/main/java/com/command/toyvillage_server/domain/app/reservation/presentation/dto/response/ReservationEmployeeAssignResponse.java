package com.command.toyvillage_server.domain.app.reservation.presentation.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record ReservationEmployeeAssignResponse(
    List<ReservationPermissionResponse> assigned,
    List<ReservationPermissionResponse> assignable
) {
    public static ReservationEmployeeAssignResponse of(
        List<ReservationPermissionResponse> assigned,
        List<ReservationPermissionResponse> assignable
    ) {
        return ReservationEmployeeAssignResponse.builder()
            .assigned(assigned)
            .assignable(assignable)
            .build();
    }
}

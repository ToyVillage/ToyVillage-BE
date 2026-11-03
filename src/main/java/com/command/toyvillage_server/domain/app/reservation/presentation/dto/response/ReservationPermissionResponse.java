package com.command.toyvillage_server.domain.app.reservation.presentation.dto.response;

import lombok.Builder;

@Builder
public record ReservationPermissionResponse(
    Long appAdminId,
    String name
) {
    public static ReservationPermissionResponse of(Long appAdminId, String name){
        return ReservationPermissionResponse.builder()
            .appAdminId(appAdminId)
            .name(name)
            .build();
    }
}

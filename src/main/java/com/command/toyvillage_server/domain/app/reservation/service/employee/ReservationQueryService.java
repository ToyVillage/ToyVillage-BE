package com.command.toyvillage_server.domain.app.reservation.service.employee;

import com.command.toyvillage_server.domain.app.auth.admin.exception.AppAdminNotFoundException;
import com.command.toyvillage_server.domain.app.reservation.domain.Reservation;
import com.command.toyvillage_server.domain.app.reservation.domain.ReservationPermission;
import com.command.toyvillage_server.domain.app.reservation.domain.repository.ReservationPermissionRepository;
import com.command.toyvillage_server.domain.app.reservation.exception.ReservationNotFoundException;
import com.command.toyvillage_server.domain.app.reservation.presentation.dto.response.ReservationResponse;
import com.command.toyvillage_server.global.security.auth.AppAdminDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReservationQueryService {
    private final ReservationPermissionRepository reservationPermissionRepository;

    @Transactional(readOnly = true)
    public ReservationResponse execute(Long reservationId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !(authentication.getPrincipal() instanceof AppAdminDetails appAdminDetails)) {
            throw AppAdminNotFoundException.EXCEPTION;
        }

        Reservation reservation = reservationPermissionRepository
            .findByReservation_IdAndAppAdmin_Id(reservationId, appAdminDetails.getId())
            .map(ReservationPermission::getReservation)
            .orElseThrow(() -> ReservationNotFoundException.EXCEPTION);

        return ReservationResponse.from(reservation);
    }
}

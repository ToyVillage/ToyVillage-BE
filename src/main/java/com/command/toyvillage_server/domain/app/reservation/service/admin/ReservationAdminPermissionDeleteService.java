package com.command.toyvillage_server.domain.app.reservation.service.admin;

import com.command.toyvillage_server.domain.app.reservation.domain.ReservationPermission;
import com.command.toyvillage_server.domain.app.reservation.domain.repository.ReservationPermissionRepository;
import com.command.toyvillage_server.domain.app.reservation.exception.ReservationPermissionNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReservationAdminPermissionDeleteService {
    private final ReservationPermissionRepository reservationPermissionRepository;

    @Transactional
    public void execute(Long reservationId, Long appAdminId) {
        ReservationPermission permission = reservationPermissionRepository
            .findByReservation_IdAndAppAdmin_Id(reservationId, appAdminId)
            .orElseThrow(() -> ReservationPermissionNotFoundException.EXCEPTION);

        reservationPermissionRepository.delete(permission);
    }
}

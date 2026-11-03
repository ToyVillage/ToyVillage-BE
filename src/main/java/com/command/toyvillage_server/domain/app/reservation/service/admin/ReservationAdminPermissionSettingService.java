package com.command.toyvillage_server.domain.app.reservation.service.admin;

import com.command.toyvillage_server.domain.app.auth.admin.domain.AppAdmin;
import com.command.toyvillage_server.domain.app.auth.admin.domain.repository.AppAdminRepository;
import com.command.toyvillage_server.domain.app.auth.admin.exception.AppAdminNotFoundException;
import com.command.toyvillage_server.domain.app.reservation.domain.Reservation;
import com.command.toyvillage_server.domain.app.reservation.domain.ReservationPermission;
import com.command.toyvillage_server.domain.app.reservation.domain.repository.ReservationPermissionRepository;
import com.command.toyvillage_server.domain.app.reservation.domain.repository.ReservationRepository;
import com.command.toyvillage_server.domain.app.reservation.exception.ReservationNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationAdminPermissionSettingService {
    private final ReservationRepository reservationRepository;
    private final ReservationPermissionRepository reservationPermissionRepository;
    private final AppAdminRepository appAdminRepository;

    @Transactional
    public void execute(Long reservationId, Long appAdminId, boolean permission) {
        Reservation reservation = reservationRepository.findById(reservationId)
            .orElseThrow(() -> ReservationNotFoundException.EXCEPTION);

        AppAdmin appAdmin = appAdminRepository.findByIdAndDeleteStatusFalse(appAdminId)
            .orElseThrow(() -> AppAdminNotFoundException.EXCEPTION);

        if (permission) {
            grant(reservation, appAdmin);
        } else {
            revoke(reservationId, appAdminId);
        }
    }

    private void grant(Reservation reservation, AppAdmin appAdmin) {
        if (!reservationPermissionRepository.existsByReservation_IdAndAppAdmin_Id(
                reservation.getId(),
                appAdmin.getId()
        )) {
            reservationPermissionRepository.save(
                ReservationPermission.builder()
                    .reservation(reservation)
                    .appAdmin(appAdmin)
                    .build()
            );
        }
    }

    private void revoke(Long reservationId, Long appAdminId) {
        reservationPermissionRepository
            .findByReservation_IdAndAppAdmin_Id(reservationId, appAdminId)
            .ifPresent(reservationPermissionRepository::delete);
    }
}

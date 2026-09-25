package com.command.toyvillage_server.domain.app.reservation.service.admin;

import com.command.toyvillage_server.domain.app.auth.admin.domain.AppAdmin;
import com.command.toyvillage_server.domain.app.auth.admin.domain.repository.AppAdminRepository;
import com.command.toyvillage_server.domain.app.auth.admin.exception.AppAdminNotFoundException;
import com.command.toyvillage_server.domain.app.reservation.domain.Reservation;
import com.command.toyvillage_server.domain.app.reservation.domain.ReservationPermission;
import com.command.toyvillage_server.domain.app.reservation.domain.repository.ReservationPermissionRepository;
import com.command.toyvillage_server.domain.app.reservation.domain.repository.ReservationRepository;
import com.command.toyvillage_server.domain.app.reservation.exception.ReservationNotFoundException;
import com.command.toyvillage_server.domain.app.reservation.presentation.dto.request.ReservationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationAdminUpdateService {
    private final ReservationRepository reservationRepository;
    private final ReservationPermissionRepository reservationPermissionRepository;
    private final AppAdminRepository appAdminRepository;

    @Transactional
    public void execute(Long reservationId, ReservationRequest request) {
        ReservationAdminCreateService.validate(request);

        Reservation reservation = reservationRepository.findById(reservationId)
            .orElseThrow(() -> ReservationNotFoundException.EXCEPTION);

        reservation.update(
            request.title(),
            request.location(),
            request.counselDate(),
            request.reservationName(),
            request.leaderPhoneNumber(),
            request.reservationCount(),
            request.leaderCount(),
            request.money(),
            request.visitDate(),
            request.visitTime(),
            request.exitTime(),
            request.visitSiteCount(),
            request.visitSiteDate(),
            request.visitSiteTime(),
            request.visitSiteExitTime()
        );

        replacePermissions(reservation, request.appAdminIds());
    }

    private void replacePermissions(Reservation reservation, List<Long> appAdminIds) {
        reservationPermissionRepository.deleteAllByReservation_Id(reservation.getId());
        reservationPermissionRepository.flush();

        appAdminIds.stream()
            .distinct()
            .forEach(appAdminId -> {
                AppAdmin appAdmin = appAdminRepository.findByIdAndDeleteStatusFalse(appAdminId)
                    .orElseThrow(() -> AppAdminNotFoundException.EXCEPTION);

                reservationPermissionRepository.save(
                    ReservationPermission.builder()
                        .reservation(reservation)
                        .appAdmin(appAdmin)
                        .build()
                );
            });
    }
}

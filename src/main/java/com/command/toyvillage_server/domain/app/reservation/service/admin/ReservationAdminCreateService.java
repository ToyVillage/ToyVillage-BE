package com.command.toyvillage_server.domain.app.reservation.service.admin;

import com.command.toyvillage_server.domain.app.auth.admin.domain.AppAdmin;
import com.command.toyvillage_server.domain.app.auth.admin.domain.repository.AppAdminRepository;
import com.command.toyvillage_server.domain.app.auth.admin.exception.AppAdminNotFoundException;
import com.command.toyvillage_server.domain.app.reservation.domain.Reservation;
import com.command.toyvillage_server.domain.app.reservation.domain.ReservationPermission;
import com.command.toyvillage_server.domain.app.reservation.domain.repository.ReservationPermissionRepository;
import com.command.toyvillage_server.domain.app.reservation.domain.repository.ReservationRepository;
import com.command.toyvillage_server.domain.app.reservation.exception.ReservationInvalidDateException;
import com.command.toyvillage_server.domain.app.reservation.exception.ReservationInvalidTimeException;
import com.command.toyvillage_server.domain.app.reservation.presentation.dto.request.ReservationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationAdminCreateService {
    private final ReservationRepository reservationRepository;
    private final ReservationPermissionRepository reservationPermissionRepository;
    private final AppAdminRepository appAdminRepository;

    @Transactional
    public Long execute(ReservationRequest request) {
        validate(request);

        Reservation reservation = reservationRepository.save(
            Reservation.builder()
                .title(request.title())
                .location(request.location())
                .counselDate(request.counselDate())
                .reservationName(request.reservationName())
                .leaderPhoneNumber(request.leaderPhoneNumber())
                .reservationCount(request.reservationCount())
                .leaderCount(request.leaderCount())
                .money(request.money())
                .visitDate(request.visitDate())
                .visitTime(request.visitTime())
                .exitTime(request.exitTime())
                .visitSiteCount(request.visitSiteCount())
                .visitSiteDate(request.visitSiteDate())
                .visitSiteTime(request.visitSiteTime())
                .visitSiteExitTime(request.visitSiteExitTime())
                .build()
        );

        grantPermissions(reservation, request.appAdminIds());

        return reservation.getId();
    }

    static void validate(ReservationRequest request) {
        if (!request.exitTime().isAfter(request.visitTime())) {
            throw ReservationInvalidTimeException.EXCEPTION;
        }

        if (!request.visitSiteExitTime().isAfter(request.visitSiteTime())) {
            throw ReservationInvalidTimeException.EXCEPTION;
        }

        if (request.visitSiteDate().isAfter(request.visitDate())) {
            throw ReservationInvalidDateException.EXCEPTION;
        }
    }

    private void grantPermissions(Reservation reservation, List<Long> appAdminIds) {
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

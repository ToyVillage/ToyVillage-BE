package com.command.toyvillage_server.domain.app.reservation.service.admin;

import com.command.toyvillage_server.domain.app.reservation.domain.repository.ReservationPermissionRepository;
import com.command.toyvillage_server.domain.app.reservation.domain.repository.ReservationRepository;
import com.command.toyvillage_server.domain.app.reservation.exception.ReservationNotFoundException;
import com.command.toyvillage_server.domain.app.reservation.presentation.dto.response.ReservationPermissionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationAdminPermissionQueryListService {
    private final ReservationRepository reservationRepository;
    private final ReservationPermissionRepository reservationPermissionRepository;

    @Transactional(readOnly = true)
    public List<ReservationPermissionResponse> execute(Long reservationId) {
        reservationRepository.findById(reservationId)
            .orElseThrow(() -> ReservationNotFoundException.EXCEPTION);

        return reservationPermissionRepository.findAllByReservation_IdAndAppAdmin_DeleteStatusFalse(reservationId)
            .stream()
            .map(permission -> ReservationPermissionResponse.of(
                    permission.getAppAdmin().getId(),
                    permission.getAppAdmin().getName()
            ))
            .toList();
    }
}

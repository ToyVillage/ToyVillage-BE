package com.command.toyvillage_server.domain.app.reservation.service.admin;

import com.command.toyvillage_server.domain.app.auth.admin.domain.AppAdmin;
import com.command.toyvillage_server.domain.app.auth.admin.domain.AppAdminRole;
import com.command.toyvillage_server.domain.app.auth.admin.domain.repository.AppAdminRepository;
import com.command.toyvillage_server.domain.app.reservation.domain.ReservationPermission;
import com.command.toyvillage_server.domain.app.reservation.domain.repository.ReservationPermissionRepository;
import com.command.toyvillage_server.domain.app.reservation.domain.repository.ReservationRepository;
import com.command.toyvillage_server.domain.app.reservation.exception.ReservationNotFoundException;
import com.command.toyvillage_server.domain.app.reservation.presentation.dto.response.ReservationEmployeeAssignResponse;
import com.command.toyvillage_server.domain.app.reservation.presentation.dto.response.ReservationPermissionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ReservationAdminEmployeeQueryListService {
    private static final long NEW_RESERVATION_ID = -1L;

    private final ReservationRepository reservationRepository;
    private final ReservationPermissionRepository reservationPermissionRepository;
    private final AppAdminRepository appAdminRepository;

    @Transactional(readOnly = true)
    public ReservationEmployeeAssignResponse execute(Long reservationId) {
        Set<Long> assignedIds = findAssignedIds(reservationId);

        List<ReservationPermissionResponse> assigned = new ArrayList<>();
        List<ReservationPermissionResponse> assignable = new ArrayList<>();

        for (AppAdmin employee : appAdminRepository.findByRoleOrderByNameAsc(AppAdminRole.EMPLOYEE)) {
            if (assignedIds.contains(employee.getId())) {
                assigned.add(toResponse(employee));
            } else {
                assignable.add(toResponse(employee));
            }
        }

        return ReservationEmployeeAssignResponse.of(assigned, assignable);
    }

    private Set<Long> findAssignedIds(Long reservationId) {
        if (reservationId == NEW_RESERVATION_ID) {
            return Set.of();
        }

        if (!reservationRepository.existsById(reservationId)) {
            throw ReservationNotFoundException.EXCEPTION;
        }

        Set<Long> assignedIds = new HashSet<>();

        for (ReservationPermission permission : reservationPermissionRepository.findAllByReservation_Id(reservationId)) {
            assignedIds.add(permission.getAppAdmin().getId());
        }

        return assignedIds;
    }

    private ReservationPermissionResponse toResponse(AppAdmin appAdmin) {
        return ReservationPermissionResponse.of(appAdmin.getId(), appAdmin.getName());
    }
}

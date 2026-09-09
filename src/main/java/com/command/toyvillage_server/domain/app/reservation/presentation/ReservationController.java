package com.command.toyvillage_server.domain.app.reservation.presentation;

import com.command.toyvillage_server.domain.app.reservation.domain.enums.ReservationSortType;
import com.command.toyvillage_server.domain.app.reservation.domain.enums.ReservationStatus;
import com.command.toyvillage_server.domain.app.reservation.presentation.dto.request.ReservationRequest;
import com.command.toyvillage_server.domain.app.reservation.presentation.dto.response.ReservationAdminQueryListResponse;
import com.command.toyvillage_server.domain.app.reservation.presentation.dto.response.ReservationEmployeeAssignResponse;
import com.command.toyvillage_server.domain.app.reservation.presentation.dto.response.ReservationAdminQueryResponse;
import com.command.toyvillage_server.domain.app.reservation.presentation.dto.response.ReservationListResponse;
import com.command.toyvillage_server.domain.app.reservation.presentation.dto.response.ReservationPermissionResponse;
import com.command.toyvillage_server.domain.app.reservation.presentation.dto.response.ReservationResponse;
import com.command.toyvillage_server.domain.app.reservation.service.admin.ReservationAdminCreateService;
import com.command.toyvillage_server.domain.app.reservation.service.admin.ReservationAdminDeleteService;
import com.command.toyvillage_server.domain.app.reservation.service.admin.ReservationAdminEmployeeQueryListService;
import com.command.toyvillage_server.domain.app.reservation.service.admin.ReservationAdminPermissionDeleteService;
import com.command.toyvillage_server.domain.app.reservation.service.admin.ReservationAdminUpdateService;
import com.command.toyvillage_server.domain.app.reservation.service.admin.ReservationAdminPermissionQueryListService;
import com.command.toyvillage_server.domain.app.reservation.service.admin.ReservationAdminPermissionSettingService;
import com.command.toyvillage_server.domain.app.reservation.service.admin.ReservationAdminQueryListService;
import com.command.toyvillage_server.domain.app.reservation.service.admin.ReservationAdminQueryService;
import com.command.toyvillage_server.domain.app.reservation.service.employee.ReservationQueryListService;
import com.command.toyvillage_server.domain.app.reservation.service.employee.ReservationQueryService;
import com.command.toyvillage_server.global.common.response.MessageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reservation")
public class ReservationController {
    private final ReservationQueryService reservationQueryService;
    private final ReservationQueryListService reservationQueryListService;
    private final ReservationAdminPermissionSettingService reservationAdminPermissionSettingService;
    private final ReservationAdminPermissionQueryListService reservationAdminPermissionQueryListService;
    private final ReservationAdminPermissionDeleteService reservationAdminPermissionDeleteService;
    private final ReservationAdminQueryListService reservationAdminQueryListService;
    private final ReservationAdminQueryService reservationAdminQueryService;
    private final ReservationAdminCreateService reservationAdminCreateService;
    private final ReservationAdminUpdateService reservationAdminUpdateService;
    private final ReservationAdminDeleteService reservationAdminDeleteService;
    private final ReservationAdminEmployeeQueryListService reservationAdminEmployeeQueryListService;

    @GetMapping("/employee/{id}")
    public ReservationResponse getDetail(@PathVariable Long id) {
        return reservationQueryService.execute(id);
    }

    @GetMapping("/employee")
    public List<ReservationListResponse> getList() {
        return reservationQueryListService.execute();
    }

    @GetMapping
    public ReservationAdminQueryListResponse getReservationList(
        @RequestParam(required = false) ReservationStatus status,
        @RequestParam(required = false) String title,
        @RequestParam(required = false) ReservationSortType sort,
        @PageableDefault(page = 0, size = 3) Pageable pageable
    ) {
        return reservationAdminQueryListService.execute(status, title, sort, pageable);
    }

    @GetMapping("/{id}")
    public ReservationAdminQueryResponse getReservationDetail(@PathVariable Long id) {
        return reservationAdminQueryService.execute(id);
    }

    @PostMapping("/permission/{reservationId}/{appAdminId}")
    public void setReservationPermission(
        @PathVariable Long reservationId,
        @PathVariable Long appAdminId,
        @RequestBody boolean reservationPermission
    ) {
        reservationAdminPermissionSettingService.execute(reservationId, appAdminId, reservationPermission);
    }

    @DeleteMapping("/permission/{reservationId}/{appAdminId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePermission(
        @PathVariable Long reservationId,
        @PathVariable Long appAdminId
    ) {
        reservationAdminPermissionDeleteService.execute(reservationId, appAdminId);
    }

    @GetMapping("/permission/{reservationId}")
    public List<ReservationPermissionResponse> getPermissionList(@PathVariable Long reservationId) {
        return reservationAdminPermissionQueryListService.execute(reservationId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MessageResponse createReservation(@RequestBody @Valid ReservationRequest request) {
        reservationAdminCreateService.execute(request);

        return MessageResponse.of("단체예약 생성이 완료되었습니다.");
    }

    @PatchMapping("/{reservationId}")
    public MessageResponse updateReservation(
        @PathVariable Long reservationId,
        @RequestBody @Valid ReservationRequest request
    ) {
        reservationAdminUpdateService.execute(reservationId, request);

        return MessageResponse.of("단체예약 수정이 완료되었습니다.");
    }

    @DeleteMapping("/{reservationId}")
    public MessageResponse deleteReservation(@PathVariable Long reservationId) {
        reservationAdminDeleteService.execute(reservationId);

        return MessageResponse.of("단체예약 삭제가 완료되었습니다.");
    }

    @GetMapping("/assigned-employee/{reservationId}")
    public ReservationEmployeeAssignResponse getAssignedEmployees(
        @PathVariable Long reservationId
    ) {
        return reservationAdminEmployeeQueryListService.execute(reservationId);
    }
}

package com.command.toyvillage_server.domain.app.reservation.service.admin;

import com.command.toyvillage_server.domain.app.reservation.domain.enums.ReservationSortType;
import com.command.toyvillage_server.domain.app.reservation.domain.enums.ReservationStatus;
import com.command.toyvillage_server.domain.app.reservation.domain.repository.ReservationRepository;
import com.command.toyvillage_server.domain.app.reservation.presentation.dto.response.ReservationAdminQueryListObjectResponse;
import com.command.toyvillage_server.domain.app.reservation.presentation.dto.response.ReservationAdminQueryListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ReservationAdminQueryListService {
    private final ReservationRepository reservationRepository;

    @Transactional
    public ReservationAdminQueryListResponse execute(
        ReservationStatus status,
        String title,
        ReservationSortType sortType,
        Pageable pageable
    ) {
        LocalDate today = LocalDate.now();

        reservationRepository.updateBeforeSiteVisitStatus(today, ReservationStatus.BEFORE_SITE_VISIT);
        reservationRepository.updateSiteVisitCompletedStatus(today, ReservationStatus.SITE_VISIT_COMPLETED);
        reservationRepository.updateVisitCompletedStatus(today, ReservationStatus.VISIT_COMPLETED);

        int beforeVisitSite = reservationRepository.countByStatus(ReservationStatus.BEFORE_SITE_VISIT);
        int doneVisitSite = reservationRepository.countByStatus(ReservationStatus.SITE_VISIT_COMPLETED);
        int doneVisit = reservationRepository.countByStatus(ReservationStatus.VISIT_COMPLETED);

        Page<ReservationAdminQueryListObjectResponse> reservations = reservationRepository
            .search(status, emptyToNull(title), withSort(pageable, sortType))
            .map(ReservationAdminQueryListObjectResponse::from);

        return ReservationAdminQueryListResponse.of(beforeVisitSite, doneVisitSite, doneVisit, reservations);
    }

    private Pageable withSort(Pageable pageable, ReservationSortType sortType) {
        ReservationSortType applied = sortType == null ? ReservationSortType.RESERVATION_DATE : sortType;

        return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), applied.toSort());
    }

    private String emptyToNull(String title) {
        return title == null || title.isBlank() ? null : title.trim();
    }
}

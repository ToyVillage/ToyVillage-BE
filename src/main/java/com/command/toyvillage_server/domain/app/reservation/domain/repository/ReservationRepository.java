package com.command.toyvillage_server.domain.app.reservation.domain.repository;

import com.command.toyvillage_server.domain.app.reservation.domain.Reservation;
import com.command.toyvillage_server.domain.app.reservation.domain.enums.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    Optional<Reservation> findById(Long id);

    int countByStatus(ReservationStatus status);

    @Query("""
        select r from Reservation r
        where (:status is null or r.status = :status)
          and (:title is null or lower(r.title) like lower(concat('%', :title, '%')))
        """)
    Page<Reservation> search(
        @Param("status") ReservationStatus status,
        @Param("title") String title,
        Pageable pageable
    );

    @Modifying
    @Query("""
        update Reservation r
        set r.status = :status
        where r.visitSiteDate >= :today
          and r.visitDate >= :today
          and (r.status is null or r.status <> :status)
        """)
    int updateBeforeSiteVisitStatus(
        @Param("today") LocalDate today,
        @Param("status") ReservationStatus status
    );

    @Modifying
    @Query("""
        update Reservation r
        set r.status = :status
        where r.visitSiteDate < :today
          and r.visitDate >= :today
          and (r.status is null or r.status <> :status)
        """)
    int updateSiteVisitCompletedStatus(
        @Param("today") LocalDate today,
        @Param("status") ReservationStatus status
    );

    @Modifying
    @Query("""
        update Reservation r
        set r.status = :status
        where r.visitDate < :today
          and (r.status is null or r.status <> :status)
        """)
    int updateVisitCompletedStatus(
        @Param("today") LocalDate today,
        @Param("status") ReservationStatus status
    );
}

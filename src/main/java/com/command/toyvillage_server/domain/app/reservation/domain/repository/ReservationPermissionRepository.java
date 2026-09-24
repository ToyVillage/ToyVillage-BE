package com.command.toyvillage_server.domain.app.reservation.domain.repository;

import com.command.toyvillage_server.domain.app.reservation.domain.Reservation;
import com.command.toyvillage_server.domain.app.reservation.domain.ReservationPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface ReservationPermissionRepository extends JpaRepository<ReservationPermission, Long> {
    boolean existsByReservation_IdAndAppAdmin_Id(Long reservationId, Long appAdminId);

    @Query("""
            select rp.reservation from ReservationPermission rp
            where rp.appAdmin.id = :appAdminId and rp.appAdmin.deleteStatus = false
            """)
    List<Reservation> findReservationsByAppAdminId(@Param("appAdminId") Long appAdminId);

    List<ReservationPermission> findAllByReservation_IdAndAppAdmin_DeleteStatusFalse(Long reservationId);

    void deleteAllByReservation_Id(Long reservationId);

    Optional<ReservationPermission> findByReservation_IdAndAppAdmin_Id(
            Long reservationId,
            Long appAdminId
    );
}

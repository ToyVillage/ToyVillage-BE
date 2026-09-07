package com.command.toyvillage_server.domain.app.reservation.domain;

import com.command.toyvillage_server.domain.app.reservation.domain.enums.ReservationStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Table(name = "tbl_reservation")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, name = "reservation_name")
    private String reservationName;

    @Column(nullable = false, name = "reservation_count")
    private Integer reservationCount;

    @Column(nullable = false, name = "reservation_date")
    private LocalDate reservationDate;

    @Column(nullable = false, name = "reservation_time")
    private LocalTime reservationTime;

    @Column(nullable = false, name = "visit_site_date")
    private LocalDate visitSiteDate;

    @Column(nullable = false, name = "visit_site_time")
    private LocalTime visitSiteTime;

    @Column(nullable = false, name = "visit_site_exit_time")
    private LocalTime visitSiteExitTime;

    @Column(nullable = false, name = "visit_site_count")
    private Integer visitSiteCount;

    @Column(nullable = false, name = "leader_count")
    private Integer leaderCount;

    @Column(nullable = false, name = "leader_phone_number", length = 15)
    private String leaderPhoneNumber;

    @Column(nullable = false, name = "visit_date")
    private LocalDate visitDate;

    @Column(nullable = false, name = "visit_time")
    private LocalTime visitTime;

    @Column(nullable = false, name = "out_time")
    private LocalTime exitTime;

    @Column(nullable = false, name = "counsel_date")
    private LocalDate counselDate;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private Integer money;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "status")
    private ReservationStatus status;

    @PrePersist
    private void initCreatedValues() {
        LocalDateTime now = LocalDateTime.now();

        this.reservationDate = now.toLocalDate();
        this.reservationTime = now.toLocalTime().withNano(0);

        updateStatus(this.reservationDate);
    }

    public void update(
        String title,
        String location,
        LocalDate counselDate,
        String reservationName,
        String leaderPhoneNumber,
        Integer reservationCount,
        Integer leaderCount,
        Integer money,
        LocalDate visitDate,
        LocalTime visitTime,
        LocalTime exitTime,
        Integer visitSiteCount,
        LocalDate visitSiteDate,
        LocalTime visitSiteTime,
        LocalTime visitSiteExitTime
    ) {
        this.title = title;
        this.location = location;
        this.counselDate = counselDate;
        this.reservationName = reservationName;
        this.leaderPhoneNumber = leaderPhoneNumber;
        this.reservationCount = reservationCount;
        this.leaderCount = leaderCount;
        this.money = money;
        this.visitDate = visitDate;
        this.visitTime = visitTime;
        this.exitTime = exitTime;
        this.visitSiteCount = visitSiteCount;
        this.visitSiteDate = visitSiteDate;
        this.visitSiteTime = visitSiteTime;
        this.visitSiteExitTime = visitSiteExitTime;

        updateStatus(LocalDate.now());
    }

    public void updateStatus(LocalDate today) {
        this.status = ReservationStatus.from(visitSiteDate, visitDate, today);
    }
}

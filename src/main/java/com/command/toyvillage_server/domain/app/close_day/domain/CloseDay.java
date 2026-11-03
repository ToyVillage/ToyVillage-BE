package com.command.toyvillage_server.domain.app.close_day.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "tbl_close_day")
public class CloseDay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "start_close_time", nullable = false)
    private LocalDate startCloseTime;

    @Column(name = "end_close_time" , nullable = false)
    private LocalDate endCloseTime;

    @Builder
    private CloseDay(String title, LocalDate startCloseTime, LocalDate endCloseTime) {
        this.title = title;
        this.startCloseTime = startCloseTime;
        this.endCloseTime = endCloseTime;
    }

    public static CloseDay create(String title, LocalDate startCloseTime, LocalDate endCloseTime) {
        return CloseDay.builder()
                .title(title)
                .startCloseTime(startCloseTime)
                .endCloseTime(endCloseTime)
                .build();
    }

    public void update(String title, LocalDate startCloseTime, LocalDate endCloseTime) {
        this.title = title;
        this.startCloseTime = startCloseTime;
        this.endCloseTime = endCloseTime;
    }
}

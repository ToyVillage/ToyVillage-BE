package com.command.toyvillage_server.domain.app.feed_log.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class FeedLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false,name = "feed_date")
    private LocalDate feedDate;

    @Column(nullable = false, name = "feed_start_time")
    private LocalDateTime feedStartTime;

    @Column(nullable = false, name = "feed_end_time")
    private LocalDateTime feedEndTime;

    @Column(nullable = false, name = "feed_type")
    private String feedType;

    @Column(nullable = false, name = "feed_amount")
    private Integer feed_amount;

    @Column(nullable = false , name = "significant")
    private Integer significant;
}

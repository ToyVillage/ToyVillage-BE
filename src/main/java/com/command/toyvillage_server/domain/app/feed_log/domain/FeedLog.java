package com.command.toyvillage_server.domain.app.feed_log.domain;

import com.command.toyvillage_server.domain.app.animal_manage.domain.AnimalManage;
import com.command.toyvillage_server.domain.app.auth.admin.domain.AppAdmin;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_feed_log")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class FeedLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "feed_log_id")
    private Long id;

    @Column(nullable = false,name = "feed_date")
    private LocalDate feedDate;

    @JoinColumn(name = "animal_manage_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private AnimalManage animalManage;

    @Column(nullable = false, name = "feed_start_time")
    private LocalDateTime feedStartTime;

    @Column(nullable = false, name = "feed_type")
    private String feedType;

    @Column(nullable = false, name = "feed_amount")
    private Integer feed_amount;

    @Column(nullable = false , name = "significant")
    private Integer significant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id")
    private AppAdmin appAdmin;


    @Builder
    public FeedLog(AppAdmin appAdmin,AnimalManage animalManage, LocalDate feedDate, LocalDateTime feedStartTime,
                   String feedType, Integer feed_amount, Integer significant) {
        this.appAdmin = appAdmin;
        this.animalManage = animalManage;
        this.feedDate = feedDate;
        this.feedStartTime = feedStartTime;
        this.feedType = feedType;
        this.feed_amount = feed_amount;
        this.significant = significant;
    }

    public void update(LocalDate feedDate, LocalDateTime feedStartTime,
                       String feedType, Integer feed_amount, Integer significant) {
        this.feedDate = feedDate;
        this.feedStartTime = feedStartTime;
        this.feedType = feedType;
        this.feed_amount = feed_amount;
        this.significant = significant;
    }
}

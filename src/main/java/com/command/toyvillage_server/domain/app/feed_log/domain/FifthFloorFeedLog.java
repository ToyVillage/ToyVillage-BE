package com.command.toyvillage_server.domain.app.feed_log.domain;

import com.command.toyvillage_server.domain.app.animal_manage.domain.AnimalManage;
import com.command.toyvillage_server.domain.app.animal_manage.domain.enums.AnimalTaxonomic;
import com.command.toyvillage_server.domain.app.auth.admin.domain.AppAdmin;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "tbl_fifth_floor_feed_log")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class FifthFloorFeedLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "fifth_floor_feed_log_id")
    private Long id;

    @Column(nullable = false, name = "feed_date")
    private LocalDate feedDate;

    // 동물이 삭제되면 null이 되고, 아래 스냅샷으로 기록을 유지한다.
    @JoinColumn(name = "animal_manage_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private AnimalManage animalManage;

    @Column(nullable = false, name = "animal_name")
    private String animalName;

    @Column(nullable = false, name = "animal_kind_name")
    private String animalKindName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "animal_taxonomic")
    private AnimalTaxonomic animalTaxonomic;

    @Column(nullable = false, name = "animal_deleted")
    private boolean animalDeleted;

    @Column(nullable = false, name = "feed_type")
    private String feedType;

    @Column(nullable = false, name = "feed_amount")
    private Float feedAmount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "feed_unit")
    private Unit feedUnit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id")
    private AppAdmin appAdmin;

    @Builder
    public FifthFloorFeedLog(AppAdmin appAdmin, AnimalManage animalManage, LocalDate feedDate,
                             String feedType, Float feedAmount, Unit feedUnit) {
        this.appAdmin = appAdmin;
        this.animalManage = animalManage;
        this.animalName = animalManage.getAnimalName();
        this.animalKindName = animalManage.getAnimalKind().getKindName();
        this.animalTaxonomic = animalManage.getAnimalKind().getAnimalTaxonomic();
        this.feedDate = feedDate;
        this.feedType = feedType;
        this.feedAmount = feedAmount;
        this.feedUnit = feedUnit;
    }

    public void update(LocalDate feedDate, String feedType, Float feedAmount, Unit feedUnit) {
        this.feedDate = feedDate;
        this.feedType = feedType;
        this.feedAmount = feedAmount;
        this.feedUnit = feedUnit;
    }
}

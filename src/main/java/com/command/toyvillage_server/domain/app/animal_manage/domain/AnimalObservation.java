package com.command.toyvillage_server.domain.app.animal_manage.domain;

import com.command.toyvillage_server.domain.app.auth.admin.domain.AppAdmin;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "tbl_animal_observation")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AnimalObservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "animal_observation_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "animal_manage_id", nullable = false)
    private AnimalManage animalManage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private AppAdmin author;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false, length = 2000)
    private String content;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

    @Builder
    private AnimalObservation(
        AnimalManage animalManage,
        AppAdmin author,
        String title,
        String content,
        LocalDateTime createdAt
    ) {
        this.animalManage = animalManage;
        this.author = author;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
    }
}

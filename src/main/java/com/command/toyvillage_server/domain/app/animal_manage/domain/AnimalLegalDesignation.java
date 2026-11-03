package com.command.toyvillage_server.domain.app.animal_manage.domain;

import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "tbl_animal_legal_designation")
public class AnimalLegalDesignation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "animal_legal_status_id", nullable = false)
    private AnimalLegalStatus animalLegalStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "animal_kind_id", nullable = false)
    private AnimalKind animalKind;

    @Builder
    private AnimalLegalDesignation(
        AnimalLegalStatus animalLegalStatus,
        AnimalKind animalKind
    ) {
        this.animalLegalStatus = animalLegalStatus;
        this.animalKind = animalKind;
    }
}

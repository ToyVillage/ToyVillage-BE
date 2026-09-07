package com.command.toyvillage_server.domain.app.animal_manage.domain;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
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
}

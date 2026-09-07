package com.command.toyvillage_server.domain.app.animal_manage.domain;

import com.command.toyvillage_server.domain.app.animal_manage.domain.enums.AnimalTaxonomic;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Table(name = "tbl_animal_kind")
public class AnimalKind {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "kind_name", nullable = false)
    private String kindName;

    @Column(name = "eng_name", nullable = false)
    private String engName;

    @Column(name = "scientific_name", nullable = false)
    private String scientificName;

    @Column(name = "animal_taxonomic", nullable = false)
    @Enumerated(EnumType.STRING)
    private AnimalTaxonomic animalTaxonomic;

    @Column(name = "detail_kind",  nullable = false)
    private String detailKind;
}

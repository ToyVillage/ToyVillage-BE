package com.command.toyvillage_server.domain.app.animal_manage.domain;

import com.command.toyvillage_server.domain.app.animal_manage.domain.enums.AnimalTaxonomic;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Table(name = "tbl_animal_manage")
public class AnimalManage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "animal_name", nullable = false)
    private String animalName;

    @Column(name = "animal_eng_name", nullable = false)
    private String animalEngName;

    @Column(name = "animal_scientific_name", nullable = false)
    private String animalScientificName;

    @Column(name = "animal_taxonomic", nullable = false)
    private AnimalTaxonomic animalTaxonomic;

    @Column(name = "animal_detail_kind",  nullable = false)
    private String animalDetailKind;
}

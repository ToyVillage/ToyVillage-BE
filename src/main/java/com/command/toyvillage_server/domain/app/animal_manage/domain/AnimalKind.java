package com.command.toyvillage_server.domain.app.animal_manage.domain;

import com.command.toyvillage_server.domain.app.animal_manage.domain.enums.AnimalTaxonomic;
import com.command.toyvillage_server.domain.web.file.domain.File;
import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "file_id", nullable = false)
    private File kindImage;

    @Builder
    private AnimalKind(
        String kindName,
        String engName,
        String scientificName,
        AnimalTaxonomic animalTaxonomic,
        String detailKind,
        File kindImage
    ) {
        this.kindName = kindName;
        this.engName = engName;
        this.scientificName = scientificName;
        this.animalTaxonomic = animalTaxonomic;
        this.detailKind = detailKind;
        this.kindImage = kindImage;
    }

    public void update(
        String kindName,
        String engName,
        String scientificName,
        AnimalTaxonomic animalTaxonomic,
        String detailKind,
        File kindImage
    ) {
        this.kindName = kindName;
        this.engName = engName;
        this.scientificName = scientificName;
        this.animalTaxonomic = animalTaxonomic;
        this.detailKind = detailKind;
        this.kindImage = kindImage;
    }
}

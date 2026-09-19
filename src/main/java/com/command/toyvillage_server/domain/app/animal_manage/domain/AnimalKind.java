package com.command.toyvillage_server.domain.app.animal_manage.domain;

import com.command.toyvillage_server.domain.app.animal_manage.domain.enums.AnimalTaxonomic;
import com.command.toyvillage_server.domain.web.file.domain.File;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "tbl_animal_kind")
@EntityListeners(AuditingEntityListener.class)
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

    @Column(name = "detail_kind")
    private String detailKind;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "file_id", nullable = false)
    private File kindImage;

    @OneToMany(mappedBy = "animalKind", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AnimalManage> animalManages = new ArrayList<>();

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

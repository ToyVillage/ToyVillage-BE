package com.command.toyvillage_server.domain.app.animal_manage.domain;

import com.command.toyvillage_server.domain.app.animal_manage.domain.enums.AnimalGender;
import com.command.toyvillage_server.domain.web.file.domain.File;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "animal_kind_id", nullable = false)
    private AnimalKind animalKind;

    @Column(name = "animal_name", nullable = false)
    private String animalName;

    @Enumerated(EnumType.STRING)
    @Column(name = "animal_gender",  nullable = false)
    private AnimalGender animalGender;

    @Column(name = "birth_year",  nullable = false)
    private int birthYear;

    @Column(name = "other_info")
    private String otherInfo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "file_id", nullable = false)
    private File animalImage;

    public void update(
        AnimalKind animalKind,
        String animalName,
        AnimalGender animalGender,
        int birthYear,
        String otherInfo,
        File animalImage
    ) {
        this.animalKind = animalKind;
        this.animalName = animalName;
        this.animalGender = animalGender;
        this.birthYear = birthYear;
        this.otherInfo = otherInfo;
        this.animalImage = animalImage;
    }
}

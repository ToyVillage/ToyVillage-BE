package com.command.toyvillage_server.domain.web.animal.domain;

import com.command.toyvillage_server.domain.web.animal.domain.enums.AnimalType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tbl_animal")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "animal_id")
    private Long id;

    @Column(nullable = false, length = 50, name = "animal_kind")
    private String animalKind;

    @Column(nullable = false, name = "animal_image")
    private String animalImage;

    @Column(nullable = false, name = "animal_description")
    private String animalDescription;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "animal_type")
    private AnimalType animalType;

    @Column(nullable = false, name = "popular_animal")
    private boolean popularAnimal;

    public void update(
        String animalKind,
        String animalImage,
        String animalDescription,
        AnimalType animalType,
        boolean popularAnimal
    ) {
        this.animalKind = animalKind;
        this.animalImage = animalImage;
        this.animalDescription = animalDescription;
        this.animalType = animalType;
        this.popularAnimal = popularAnimal;
    }
}

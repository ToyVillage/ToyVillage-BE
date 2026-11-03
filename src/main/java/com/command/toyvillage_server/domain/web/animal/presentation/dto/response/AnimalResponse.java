package com.command.toyvillage_server.domain.web.animal.presentation.dto.response;

import com.command.toyvillage_server.domain.web.animal.domain.Animal;
import com.command.toyvillage_server.domain.web.animal.domain.enums.AnimalType;
import com.fasterxml.jackson.annotation.JsonProperty;

public record AnimalResponse(
        Long id,

        @JsonProperty("animal_kind")
        String animalKind,

        @JsonProperty("animal_image")
        String animalImage,

        @JsonProperty("animal_description")
        String animalDescription,

        @JsonProperty("animal_type")
        AnimalType animalType,

        @JsonProperty("popular_animal")
        boolean popularAnimal
) {
    public static AnimalResponse from(Animal animal){
        return new AnimalResponse(
                animal.getId(),
                animal.getAnimalKind(),
                animal.getAnimalImage(),
                animal.getAnimalDescription(),
                animal.getAnimalType(),
                animal.isPopularAnimal()
        );
    }
}

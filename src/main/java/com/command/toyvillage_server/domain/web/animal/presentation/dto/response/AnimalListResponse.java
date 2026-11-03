package com.command.toyvillage_server.domain.web.animal.presentation.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record AnimalListResponse(
        @JsonProperty("popular_animal")
        List<AnimalResponse> popularAnimal,

        @JsonProperty("mammals")
        List<AnimalResponse> mammals,

        @JsonProperty("reptiles")
        List<AnimalResponse> reptiles,

        @JsonProperty("fish")
        List<AnimalResponse> fish,

        @JsonProperty("birds")
        List<AnimalResponse> birds
) {
    public static AnimalListResponse from(
            List<AnimalResponse> popularAnimal,
            List<AnimalResponse> mammals,
            List<AnimalResponse> reptiles,
            List<AnimalResponse> fish,
            List<AnimalResponse> birds
    ) {
        return new AnimalListResponse(
                popularAnimal,
                mammals,
                reptiles,
                fish,
                birds
        );
    }
}

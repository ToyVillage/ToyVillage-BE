package com.command.toyvillage_server.domain.app.animal_manage.domain.enums;

import lombok.Getter;

@Getter
public enum AnimalTaxonomic {
    MAMMALS("포유류"),
    REPTILES("파충류"),
    FISH("어류"),
    BIRDS("조류");

    private final String type;

    AnimalTaxonomic(String type) {
        this.type = type;
    }
}

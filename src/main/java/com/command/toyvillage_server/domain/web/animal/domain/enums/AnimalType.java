package com.command.toyvillage_server.domain.web.animal.domain.enums;

import lombok.Getter;

@Getter
public enum AnimalType {
    MAMMALS("포유류"),
    REPTILES("파충류"),
    FISH("어류"),
    BIRDS("조류");

    private final String type;

    AnimalType(String type) {
        this.type = type;
    }
}

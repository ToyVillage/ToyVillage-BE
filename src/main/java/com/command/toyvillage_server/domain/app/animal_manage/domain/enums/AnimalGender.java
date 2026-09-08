package com.command.toyvillage_server.domain.app.animal_manage.domain.enums;

public enum AnimalGender {
    MAN("수컷"),
    WOMAN("암컷"),
    UNKNOWN("미상");

    private final String type;

    AnimalGender(String type) {
        this.type = type;
    }
}

package com.command.toyvillage_server.domain.app.animal_manage.exception;

import com.command.toyvillage_server.global.error.exception.ErrorCode;
import com.command.toyvillage_server.global.error.exception.ToyVillageException;

public class AnimalKindNotFoundException extends ToyVillageException {
    public static final ToyVillageException EXCEPTION = new AnimalKindNotFoundException();

    private AnimalKindNotFoundException() {
        super(ErrorCode.ANIMAL_KIND_NOT_FOUND);
    }
}

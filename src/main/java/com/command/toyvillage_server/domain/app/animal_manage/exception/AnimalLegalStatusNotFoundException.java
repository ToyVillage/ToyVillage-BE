package com.command.toyvillage_server.domain.app.animal_manage.exception;

import com.command.toyvillage_server.global.error.exception.ErrorCode;
import com.command.toyvillage_server.global.error.exception.ToyVillageException;

public class AnimalLegalStatusNotFoundException extends ToyVillageException {
    public static final ToyVillageException EXCEPTION = new AnimalLegalStatusNotFoundException();

    private AnimalLegalStatusNotFoundException() {
        super(ErrorCode.ANIMAL_LEGAL_STATUS_NOT_FOUND);
    }
}

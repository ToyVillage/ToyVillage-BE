package com.command.toyvillage_server.domain.app.animal_manage.exception;

import com.command.toyvillage_server.global.error.exception.ErrorCode;
import com.command.toyvillage_server.global.error.exception.ToyVillageException;

public class AnimalObservationNotFoundException extends ToyVillageException {
    public static final ToyVillageException EXCEPTION = new AnimalObservationNotFoundException();

    private AnimalObservationNotFoundException() {
        super(ErrorCode.ANIMAL_OBSERVATION_NOT_FOUND);
    }
}

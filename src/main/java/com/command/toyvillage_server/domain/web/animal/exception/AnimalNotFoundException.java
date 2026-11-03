package com.command.toyvillage_server.domain.web.animal.exception;

import com.command.toyvillage_server.global.error.exception.ErrorCode;
import com.command.toyvillage_server.global.error.exception.ToyVillageException;

public class AnimalNotFoundException extends ToyVillageException {
    public static final ToyVillageException EXCEPTION = new AnimalNotFoundException();

    public AnimalNotFoundException() {
        super(ErrorCode.ANIMAL_NOT_FOUND);
    }
}

package com.command.toyvillage_server.domain.app.animal_manage.exception;

import com.command.toyvillage_server.global.error.exception.ErrorCode;
import com.command.toyvillage_server.global.error.exception.ToyVillageException;

public class AnimalManageNotFoundException extends ToyVillageException {
    public static final ToyVillageException EXCEPTION = new AnimalManageNotFoundException();

    private AnimalManageNotFoundException() {
        super(ErrorCode.ANIMAL_MANAGE_NOT_FOUND);
    }
}

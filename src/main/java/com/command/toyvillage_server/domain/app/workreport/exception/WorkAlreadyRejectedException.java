package com.command.toyvillage_server.domain.app.workreport.exception;

import com.command.toyvillage_server.global.error.exception.ErrorCode;
import com.command.toyvillage_server.global.error.exception.ToyVillageException;

public class WorkAlreadyRejectedException extends ToyVillageException {
    public static final ToyVillageException EXCEPTION = new WorkAlreadyRejectedException();

    private WorkAlreadyRejectedException() {
        super(ErrorCode.WORK_ALREADY_REJECTED);
    }
}

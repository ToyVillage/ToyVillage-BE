package com.command.toyvillage_server.domain.app.workreport.exception;

import com.command.toyvillage_server.global.error.exception.ErrorCode;
import com.command.toyvillage_server.global.error.exception.ToyVillageException;

public class WorkNotRejectedException extends ToyVillageException {
    public static final ToyVillageException EXCEPTION = new WorkNotRejectedException();

    private WorkNotRejectedException() {
        super(ErrorCode.WORK_NOT_REJECTED);
    }
}

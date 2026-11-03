package com.command.toyvillage_server.domain.app.work_log.exception;

import com.command.toyvillage_server.global.error.exception.ErrorCode;
import com.command.toyvillage_server.global.error.exception.ToyVillageException;

public class WorkLogNotFoundException extends ToyVillageException {
    public static final ToyVillageException EXCEPTION = new WorkLogNotFoundException();

    private WorkLogNotFoundException() {
        super(ErrorCode.WORK_LOG_NOT_FOUND);
    }
}

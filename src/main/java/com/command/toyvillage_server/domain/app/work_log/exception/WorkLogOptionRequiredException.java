package com.command.toyvillage_server.domain.app.work_log.exception;

import com.command.toyvillage_server.global.error.exception.ErrorCode;
import com.command.toyvillage_server.global.error.exception.ToyVillageException;

public class WorkLogOptionRequiredException extends ToyVillageException {
    public static final ToyVillageException EXCEPTION = new WorkLogOptionRequiredException();

    private WorkLogOptionRequiredException() {
        super(ErrorCode.WORK_LOG_OPTION_REQUIRED);
    }
}

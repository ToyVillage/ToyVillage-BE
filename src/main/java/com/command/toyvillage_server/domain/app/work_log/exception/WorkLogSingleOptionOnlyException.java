package com.command.toyvillage_server.domain.app.work_log.exception;

import com.command.toyvillage_server.global.error.exception.ErrorCode;
import com.command.toyvillage_server.global.error.exception.ToyVillageException;

public class WorkLogSingleOptionOnlyException extends ToyVillageException {
    public static final ToyVillageException EXCEPTION = new WorkLogSingleOptionOnlyException();

    private WorkLogSingleOptionOnlyException() {
        super(ErrorCode.WORK_LOG_SINGLE_OPTION_ONLY);
    }
}

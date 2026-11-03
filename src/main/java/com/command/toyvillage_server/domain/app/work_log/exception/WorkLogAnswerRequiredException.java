package com.command.toyvillage_server.domain.app.work_log.exception;

import com.command.toyvillage_server.global.error.exception.ErrorCode;
import com.command.toyvillage_server.global.error.exception.ToyVillageException;

public class WorkLogAnswerRequiredException extends ToyVillageException {
    public static final ToyVillageException EXCEPTION = new WorkLogAnswerRequiredException();

    private WorkLogAnswerRequiredException() {
        super(ErrorCode.WORK_LOG_ANSWER_REQUIRED);
    }
}

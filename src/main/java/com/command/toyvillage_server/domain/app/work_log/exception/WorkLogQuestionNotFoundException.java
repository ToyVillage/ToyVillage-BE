package com.command.toyvillage_server.domain.app.work_log.exception;

import com.command.toyvillage_server.global.error.exception.ErrorCode;
import com.command.toyvillage_server.global.error.exception.ToyVillageException;

public class WorkLogQuestionNotFoundException extends ToyVillageException {
    public static final ToyVillageException EXCEPTION = new WorkLogQuestionNotFoundException();

    private WorkLogQuestionNotFoundException() {
        super(ErrorCode.WORK_LOG_QUESTION_NOT_FOUND);
    }
}

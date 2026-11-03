package com.command.toyvillage_server.domain.app.work_log.exception;

import com.command.toyvillage_server.global.error.exception.ErrorCode;
import com.command.toyvillage_server.global.error.exception.ToyVillageException;

public class WorkLogQuestionOptionNotFoundException extends ToyVillageException {
    public static final ToyVillageException EXCEPTION = new WorkLogQuestionOptionNotFoundException();

    private WorkLogQuestionOptionNotFoundException() {
        super(ErrorCode.WORK_LOG_QUESTION_OPTION_NOT_FOUND);
    }
}

package com.command.toyvillage_server.domain.app.task.exception;

import com.command.toyvillage_server.global.error.exception.ErrorCode;
import com.command.toyvillage_server.global.error.exception.ToyVillageException;

public class TaskTargetInvalidException extends ToyVillageException {
    public static final ToyVillageException EXCEPTION = new TaskTargetInvalidException();

    private TaskTargetInvalidException() {
        super(ErrorCode.TASK_TARGET_INVALID);
    }
}

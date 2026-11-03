package com.command.toyvillage_server.domain.app.task.exception;

import com.command.toyvillage_server.global.error.exception.ErrorCode;
import com.command.toyvillage_server.global.error.exception.ToyVillageException;

public class TaskNotFoundException extends ToyVillageException {
    public static final ToyVillageException EXCEPTION = new TaskNotFoundException();

    private TaskNotFoundException() {
        super(ErrorCode.TASK_NOT_FOUND);
    }
}

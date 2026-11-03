package com.command.toyvillage_server.domain.app.task.exception;

import com.command.toyvillage_server.global.error.exception.ErrorCode;
import com.command.toyvillage_server.global.error.exception.ToyVillageException;

public class TaskFcmNotSendException extends ToyVillageException {
    public static final ToyVillageException EXCEPTION = new TaskFcmNotSendException();

    public TaskFcmNotSendException() {
        super(ErrorCode.TASK_FCM_NOT_SEND);
    }
}

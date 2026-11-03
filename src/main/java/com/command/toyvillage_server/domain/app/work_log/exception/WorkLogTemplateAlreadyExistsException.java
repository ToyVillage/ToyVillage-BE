package com.command.toyvillage_server.domain.app.work_log.exception;

import com.command.toyvillage_server.global.error.exception.ErrorCode;
import com.command.toyvillage_server.global.error.exception.ToyVillageException;

public class WorkLogTemplateAlreadyExistsException extends ToyVillageException {
    public static final ToyVillageException EXCEPTION = new WorkLogTemplateAlreadyExistsException();

    private WorkLogTemplateAlreadyExistsException() {
        super(ErrorCode.WORK_LOG_TEMPLATE_EXIST);
    }
}

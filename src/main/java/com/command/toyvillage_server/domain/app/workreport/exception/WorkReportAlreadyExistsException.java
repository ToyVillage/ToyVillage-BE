package com.command.toyvillage_server.domain.app.workreport.exception;

import com.command.toyvillage_server.global.error.exception.ErrorCode;
import com.command.toyvillage_server.global.error.exception.ToyVillageException;

public class WorkReportAlreadyExistsException extends ToyVillageException {
    public static final ToyVillageException EXCEPTION = new WorkReportAlreadyExistsException();

    private WorkReportAlreadyExistsException() {
        super(ErrorCode.WORK_REPORT_ALREADY_EXISTS);
    }
}

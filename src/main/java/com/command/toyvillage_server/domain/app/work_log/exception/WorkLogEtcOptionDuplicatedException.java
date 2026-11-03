package com.command.toyvillage_server.domain.app.work_log.exception;

import com.command.toyvillage_server.global.error.exception.ErrorCode;
import com.command.toyvillage_server.global.error.exception.ToyVillageException;

public class WorkLogEtcOptionDuplicatedException extends ToyVillageException {
    public static final ToyVillageException EXCEPTION = new WorkLogEtcOptionDuplicatedException();

    private WorkLogEtcOptionDuplicatedException() {
        super(ErrorCode.WORK_LOG_ETC_OPTION_DUPLICATED);
    }
}

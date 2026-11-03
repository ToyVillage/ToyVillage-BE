package com.command.toyvillage_server.domain.app.auth.admin.exception;

import com.command.toyvillage_server.global.error.exception.ErrorCode;
import com.command.toyvillage_server.global.error.exception.ToyVillageException;

public class AppAdminAlreadyExistsException extends ToyVillageException {
    public static final ToyVillageException EXCEPTION = new AppAdminAlreadyExistsException();

    private AppAdminAlreadyExistsException() {
        super(ErrorCode.APP_ADMIN_EXIST);
    }
}

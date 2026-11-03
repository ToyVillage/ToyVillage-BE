package com.command.toyvillage_server.domain.app.auth.admin.exception;

import com.command.toyvillage_server.global.error.exception.ErrorCode;
import com.command.toyvillage_server.global.error.exception.ToyVillageException;

public class AppAdminNotFoundException extends ToyVillageException {
    public static final ToyVillageException EXCEPTION = new AppAdminNotFoundException();

    private AppAdminNotFoundException() {
        super(ErrorCode.APP_ADMIN_NOT_FOUND);
    }
}

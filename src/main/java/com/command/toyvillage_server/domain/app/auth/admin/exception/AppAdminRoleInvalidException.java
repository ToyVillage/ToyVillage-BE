package com.command.toyvillage_server.domain.app.auth.admin.exception;

import com.command.toyvillage_server.global.error.exception.ErrorCode;
import com.command.toyvillage_server.global.error.exception.ToyVillageException;

public class AppAdminRoleInvalidException extends ToyVillageException {
    public static final ToyVillageException EXCEPTION = new AppAdminRoleInvalidException();

    private AppAdminRoleInvalidException() {
        super(ErrorCode.APP_ADMIN_ROLE_INVALID);
    }
}

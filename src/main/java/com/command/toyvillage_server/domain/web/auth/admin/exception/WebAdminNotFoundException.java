package com.command.toyvillage_server.domain.web.auth.admin.exception;

import com.command.toyvillage_server.global.error.exception.ErrorCode;
import com.command.toyvillage_server.global.error.exception.ToyVillageException;

public class WebAdminNotFoundException extends ToyVillageException {
    public static final ToyVillageException EXCEPTION = new WebAdminNotFoundException();

    private WebAdminNotFoundException() {
        super(ErrorCode.WEB_ADMIN_NOT_FOUND);
    }
}

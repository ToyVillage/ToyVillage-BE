package com.command.toyvillage_server.domain.web.auth.admin.exception;

import com.command.toyvillage_server.global.error.exception.ErrorCode;
import com.command.toyvillage_server.global.error.exception.ToyVillageException;

public class LoginInfoNotMatchedException extends ToyVillageException {
    public static final ToyVillageException EXCEPTION = new LoginInfoNotMatchedException();

    public LoginInfoNotMatchedException() {
        super(ErrorCode.LOGIN_INFO_NOT_MATCHED);
    }
}

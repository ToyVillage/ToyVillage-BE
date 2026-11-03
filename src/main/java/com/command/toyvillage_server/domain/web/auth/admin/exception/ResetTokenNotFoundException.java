package com.command.toyvillage_server.domain.web.auth.admin.exception;

import com.command.toyvillage_server.global.error.exception.ErrorCode;
import com.command.toyvillage_server.global.error.exception.ToyVillageException;

public class ResetTokenNotFoundException extends ToyVillageException {
    public static final ToyVillageException EXCEPTION = new ResetTokenNotFoundException();

    public ResetTokenNotFoundException() {
        super(ErrorCode.RESET_TOKEN_NOT_FOUND);
    }
}

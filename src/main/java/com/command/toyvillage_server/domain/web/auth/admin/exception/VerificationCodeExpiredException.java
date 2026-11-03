package com.command.toyvillage_server.domain.web.auth.admin.exception;

import com.command.toyvillage_server.global.error.exception.ErrorCode;
import com.command.toyvillage_server.global.error.exception.ToyVillageException;

public class VerificationCodeExpiredException extends ToyVillageException {
    public static final ToyVillageException EXCEPTION = new VerificationCodeExpiredException();

    private VerificationCodeExpiredException() {
        super(ErrorCode.VERIFICATION_CODE_EXPIRED);
    }
}

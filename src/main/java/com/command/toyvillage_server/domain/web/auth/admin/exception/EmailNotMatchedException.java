package com.command.toyvillage_server.domain.web.auth.admin.exception;

import com.command.toyvillage_server.global.error.exception.ErrorCode;
import com.command.toyvillage_server.global.error.exception.ToyVillageException;

public class EmailNotMatchedException extends ToyVillageException {

    public static final ToyVillageException EXCEPTION = new EmailNotMatchedException();

    public EmailNotMatchedException() {
        super(ErrorCode.EMAIL_NOT_MATCHED);
    }}

package com.command.toyvillage_server.domain.web.faq.exception;

import com.command.toyvillage_server.global.error.exception.ErrorCode;
import com.command.toyvillage_server.global.error.exception.ToyVillageException;

public class FaqNotFoundException extends ToyVillageException {

    public static final ToyVillageException EXCEPTION = new FaqNotFoundException();

    private FaqNotFoundException() {
        super(ErrorCode.FAQ_NOT_FOUND);
    }
}

package com.command.toyvillage_server.domain.web.partnership.exception;

import com.command.toyvillage_server.global.error.exception.ErrorCode;
import com.command.toyvillage_server.global.error.exception.ToyVillageException;

public class PartnershipNotFoundException extends ToyVillageException {
    public static final PartnershipNotFoundException EXCEPTION = new PartnershipNotFoundException();
    public PartnershipNotFoundException() {
        super(ErrorCode.PARTNERSHIP_NOT_FOUND);
    }
}

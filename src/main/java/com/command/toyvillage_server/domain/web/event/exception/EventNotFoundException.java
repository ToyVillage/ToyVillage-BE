package com.command.toyvillage_server.domain.web.event.exception;

import com.command.toyvillage_server.global.error.exception.ErrorCode;
import com.command.toyvillage_server.global.error.exception.ToyVillageException;

public class EventNotFoundException extends ToyVillageException {
    public static final ToyVillageException EXCEPTION = new EventNotFoundException();
    public EventNotFoundException() {
        super(ErrorCode.EVENT_NOT_FOUND);
    }
}

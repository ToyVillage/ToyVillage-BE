package com.command.toyvillage_server.domain.app.feed_log.exception;

import com.command.toyvillage_server.global.error.exception.ErrorCode;
import com.command.toyvillage_server.global.error.exception.ToyVillageException;

public class FeedLogForbiddenException extends ToyVillageException {
    public static final ToyVillageException EXCEPTION = new FeedLogForbiddenException();

    private FeedLogForbiddenException() {
        super(ErrorCode.FEED_LOG_FORBIDDEN);
    }
}

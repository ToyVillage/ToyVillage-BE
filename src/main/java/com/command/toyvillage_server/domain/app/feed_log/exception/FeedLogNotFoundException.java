package com.command.toyvillage_server.domain.app.feed_log.exception;

import com.command.toyvillage_server.global.error.exception.ErrorCode;
import com.command.toyvillage_server.global.error.exception.ToyVillageException;

public class FeedLogNotFoundException extends ToyVillageException {
    public static final ToyVillageException EXCEPTION = new FeedLogNotFoundException();

    public FeedLogNotFoundException() {
        super(ErrorCode.FEED_LOG_NOT_FOUND);
    }
}

package com.command.toyvillage_server.domain.web.file.exception;

import com.command.toyvillage_server.global.error.exception.ErrorCode;
import com.command.toyvillage_server.global.error.exception.ToyVillageException;

public class FileNotFoundException extends ToyVillageException {
    public static final ToyVillageException EXCEPTION = new FileNotFoundException();

    private FileNotFoundException() {
        super(ErrorCode.FILE_NOT_FOUND);
    }
}

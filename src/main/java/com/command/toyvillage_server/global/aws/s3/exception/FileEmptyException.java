package com.command.toyvillage_server.global.aws.s3.exception;

import com.command.toyvillage_server.global.error.exception.ErrorCode;
import com.command.toyvillage_server.global.error.exception.ToyVillageException;

public class FileEmptyException extends ToyVillageException {
    public static final ToyVillageException EXCEPTION = new FileEmptyException();

    public FileEmptyException() {
        super(ErrorCode.FILE_EMPTY);
    }
}

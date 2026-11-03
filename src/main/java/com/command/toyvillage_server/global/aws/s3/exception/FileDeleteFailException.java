package com.command.toyvillage_server.global.aws.s3.exception;

import com.command.toyvillage_server.global.error.exception.ErrorCode;
import com.command.toyvillage_server.global.error.exception.ToyVillageException;

public class FileDeleteFailException extends ToyVillageException {
    public static final ToyVillageException EXCEPTION = new FileDeleteFailException();
    public FileDeleteFailException() {
        super(ErrorCode.FILE_DELETE_FAIL);
    }
}

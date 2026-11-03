package com.command.toyvillage_server.global.aws.s3.exception;

import com.command.toyvillage_server.global.error.exception.ErrorCode;
import com.command.toyvillage_server.global.error.exception.ToyVillageException;

public class FileUploadFailException extends ToyVillageException {
    public static final ToyVillageException EXCEPTION = new FileUploadFailException();

    public FileUploadFailException() {
        super(ErrorCode.FILE_UPLOAD_FAIL);
    }
}

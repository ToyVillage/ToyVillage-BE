package com.command.toyvillage_server.domain.web.popup.exception;

import com.command.toyvillage_server.global.error.exception.ErrorCode;
import com.command.toyvillage_server.global.error.exception.ToyVillageException;

public class PopUpNotFoundException extends ToyVillageException {
    public static final ToyVillageException EXCEPTION = new PopUpNotFoundException();

    public PopUpNotFoundException() {
        super(ErrorCode.POPUP_NOT_FOUND);
    }
}

package com.command.toyvillage_server.domain.web.gallery.exception;

import com.command.toyvillage_server.global.error.exception.ErrorCode;
import com.command.toyvillage_server.global.error.exception.ToyVillageException;

public class GalleryNotFoundException extends ToyVillageException {
    public static final ToyVillageException EXCEPTION = new GalleryNotFoundException();
    public GalleryNotFoundException() {
        super(ErrorCode.GALLERY_NOT_FOUND);
    }
}

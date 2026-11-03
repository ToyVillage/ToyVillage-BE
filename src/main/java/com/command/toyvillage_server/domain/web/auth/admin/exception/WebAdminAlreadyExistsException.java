package com.command.toyvillage_server.domain.web.auth.admin.exception;

import com.command.toyvillage_server.global.error.exception.ErrorCode;
import com.command.toyvillage_server.global.error.exception.ToyVillageException;

public class WebAdminAlreadyExistsException extends ToyVillageException {
  public static final ToyVillageException EXCEPTION = new WebAdminAlreadyExistsException();

  private WebAdminAlreadyExistsException() {
    super(ErrorCode.WEB_ADMIN_EXIST);
  }
}

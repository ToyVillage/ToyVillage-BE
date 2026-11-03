package com.command.toyvillage_server.domain.web.news.exception;

import com.command.toyvillage_server.global.error.exception.ErrorCode;
import com.command.toyvillage_server.global.error.exception.ToyVillageException;

public class NewsNotFoundException extends ToyVillageException {
  public static final NewsNotFoundException EXCEPTION = new NewsNotFoundException();
  public NewsNotFoundException() {
    super(ErrorCode.NEWS_NOT_FOUND);
  }
}

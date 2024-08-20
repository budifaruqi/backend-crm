package com.example.test.common.helper.response.exception;

import com.example.test.common.helper.response.MicroserviceResponseHelper;
import org.springframework.http.HttpStatus;

public class MicroserviceUnauthorizedException extends MicroserviceException {

  public MicroserviceUnauthorizedException(String type, String message) {
    super(MicroserviceResponseHelper.error(HttpStatus.UNAUTHORIZED, null, null, type, message));
  }

  public MicroserviceUnauthorizedException(String type) {
    super(MicroserviceResponseHelper.error(HttpStatus.UNAUTHORIZED, null, null, type, ""));
  }
}

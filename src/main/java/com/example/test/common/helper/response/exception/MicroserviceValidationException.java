package com.example.test.common.helper.response.exception;

import com.example.test.common.helper.response.MicroserviceResponseHelper;
import org.springframework.http.HttpStatus;

public class MicroserviceValidationException extends MicroserviceException {

  public MicroserviceValidationException(String type, String message) {
    super(MicroserviceResponseHelper.error(HttpStatus.BAD_REQUEST, null, null, type, message));
  }

  public MicroserviceValidationException(String type) {
    super(MicroserviceResponseHelper.error(HttpStatus.BAD_REQUEST, null, null, type, ""));
  }
}

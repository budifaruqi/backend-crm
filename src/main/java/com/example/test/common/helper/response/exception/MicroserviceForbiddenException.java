package com.example.test.common.helper.response.exception;


import com.example.test.common.helper.response.MicroserviceResponseHelper;
import org.springframework.http.HttpStatus;

public class MicroserviceForbiddenException extends MicroserviceException {

  public MicroserviceForbiddenException(String type, String message) {
    super(MicroserviceResponseHelper.error(HttpStatus.FORBIDDEN, null, null, type, message));
  }

  public MicroserviceForbiddenException(String type) {
    super(MicroserviceResponseHelper.error(HttpStatus.FORBIDDEN, null, null, type, ""));
  }
}

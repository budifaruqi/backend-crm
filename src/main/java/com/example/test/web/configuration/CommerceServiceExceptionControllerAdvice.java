package com.example.test.web.configuration;

import com.example.test.common.helper.response.exception.MicroserviceForbiddenException;
import com.example.test.common.helper.response.exception.MicroserviceUnauthorizedException;
import com.example.test.common.helper.response.exception.MicroserviceValidationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class CommerceServiceExceptionControllerAdvice {

  @ExceptionHandler(MicroserviceValidationException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public Object throwBadRequest(MicroserviceValidationException exception) {
    ObjectMapper mapper = new ObjectMapper();
    try {
      return DetailWebResponse.builder()
          .detail(mapper.readValue(exception.getMessage(), Map.class))
          .build();
    } catch (JsonProcessingException e) {
      return null;
    }
  }

  @ExceptionHandler(MicroserviceUnauthorizedException.class)
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  public Object throwUnauthorized(MicroserviceUnauthorizedException exception) {
    ObjectMapper mapper = new ObjectMapper();
    try {
      return DetailWebResponse.builder()
          .detail(mapper.readValue(exception.getMessage(), Map.class))
          .build();
    } catch (JsonProcessingException e) {
      return null;
    }
  }

  @ExceptionHandler(MicroserviceForbiddenException.class)
  @ResponseStatus(HttpStatus.FORBIDDEN)
  public Object throwForbidden(MicroserviceForbiddenException exception) {
    ObjectMapper mapper = new ObjectMapper();
    try {
      return DetailWebResponse.builder()
          .detail(mapper.readValue(exception.getMessage(), Map.class))
          .build();
    } catch (JsonProcessingException e) {
      return null;
    }
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  private static class DetailWebResponse<T> {

    private T detail;
  }
}

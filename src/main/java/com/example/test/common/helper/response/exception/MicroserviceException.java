package com.example.test.common.helper.response.exception;

import com.example.test.common.helper.response.MicroserviceResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public abstract class MicroserviceException extends RuntimeException {

  public MicroserviceException(MicroserviceResponse<?> response) {
    super(toJson(response));
  }

  private static String toJson(Object body) {
    ObjectMapper objectMapper = new ObjectMapper();
    try {
      return objectMapper.writeValueAsString(body);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    return "";
  }
}

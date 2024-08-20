package com.example.test.common.helper.response;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MicroserviceResponseHelper {

  public static <T> MicroserviceResponse<T> ok(T data, String statusCode, String type, String message) {
    return MicroserviceResponse.<T>builder()
        .status_code(statusCode)
        .type(type)
        .message(message)
        .data(data)
        .build();
  }

  public static <T> MicroserviceResponse<T> ok(T data, String type) {
    return MicroserviceResponse.<T>builder()
        .status_code(String.valueOf(HttpStatus.OK.value()))
        .type(type)
        .message("")
        .data(data)
        .build();
  }

  public static <T> MicroserviceResponse<T> ok(T data, String type, String message) {
    return MicroserviceResponse.<T>builder()
        .status_code(String.valueOf(HttpStatus.OK.value()))
        .type(type)
        .message("")
        .data(data)
        .build();
  }

  public static <T> MicroserviceResponse<T> error(HttpStatus statusCode, String path, String params, String type,
      String message) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    return MicroserviceResponse.<T>builder()
        .status_code(String.valueOf(statusCode.value()))
        .error(statusCode.getReasonPhrase()
            .toUpperCase())
        .timestamp(sdf.format(new Date()))
        .path(path)
        .params(params)
        .type(type)
        .message(message)
        .build();
  }

  public static <T> MicroserviceResponse<List<T>> ok(@NonNull Page<T> data, String type) {
    return MicroserviceResponse.<List<T>>builder()
        .status_code(String.valueOf(HttpStatus.OK.value()))
        .type(type)
        .message("")
        .data(data.getContent())
        .paging(toPaging(data))
        .build();
  }

  private static <T> MicroserviceResponse.Paging toPaging(@NonNull Page<T> data) {
    String[] sortElement = data.getSort()
        .toString()
        .replaceAll("\\s+", "")
        .split(":");
    return MicroserviceResponse.Paging.builder()
        .page(data.getNumber() + 1)
        .size(data.getSize())
        .totalElements(data.getTotalElements())
        .totalPages(data.getTotalPages())
        .sort(sortElement[0])
        .dir(sortElement[1])
        .build();
  }
}

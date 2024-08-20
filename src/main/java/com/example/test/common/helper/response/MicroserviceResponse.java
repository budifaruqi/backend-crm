package com.example.test.common.helper.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MicroserviceResponse<T> {

  private String status_code;

  private String error;

  private String timestamp;

  private String path;

  private String params;

  private String type;

  private String message;

  private T data;

  private Paging paging;

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class Paging {

    private int page;

    private int size;

    private long totalElements;

    private int totalPages;

    private String sort;

    private String dir;
  }
}
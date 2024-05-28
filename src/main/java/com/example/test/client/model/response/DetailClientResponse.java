package com.example.test.client.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetailClientResponse<T> {

  private DetailClientResponse detail;

  private String status_code;

  private String type;

  private String message;

  private T data;
}

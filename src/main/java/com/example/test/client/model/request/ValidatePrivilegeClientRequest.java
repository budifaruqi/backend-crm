package com.example.test.client.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ValidatePrivilegeClientRequest {

  private String serviceName;

  private String operationId;
}

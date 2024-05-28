package com.example.test.client.model.request;

import com.example.test.common.enums.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ValidatePrivilegeClientRequest {

  private List<RoleEnum> roles;

  private List<String> permissions;
}

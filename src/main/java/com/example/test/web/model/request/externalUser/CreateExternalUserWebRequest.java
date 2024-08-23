package com.example.test.web.model.request.externalUser;

import com.example.test.common.enums.AccountType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateExternalUserWebRequest {

  private String externalId;

  private AccountType type;

  private String name;

  private String username;

  private String password;

  private String email;

  private String phone;
}

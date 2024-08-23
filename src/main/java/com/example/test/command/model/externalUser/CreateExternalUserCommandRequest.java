package com.example.test.command.model.externalUser;

import com.example.test.common.enums.AccountType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateExternalUserCommandRequest {

  @NotBlank
  private String companyGroupId;

  @NotBlank
  private String salesId;

  @NotBlank
  private String externalId;

  @NotNull
  private AccountType type;

  @NotBlank
  private String name;

  @NotBlank
  private String username;

  @NotBlank
  private String password;

  @NotBlank
  private String email;

  @NotBlank
  private String phone;
}

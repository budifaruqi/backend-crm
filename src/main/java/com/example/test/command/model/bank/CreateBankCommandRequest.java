package com.example.test.command.model.bank;

import com.example.test.common.enums.BankType;
import com.example.test.validation.annotation.BankTypeCheck;
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
@BankTypeCheck
public class CreateBankCommandRequest {
  @NotBlank
  private String companyGroupId;

  @NotBlank
  private String name;

  @NotNull
  private BankType type;

  private String parentId;
}

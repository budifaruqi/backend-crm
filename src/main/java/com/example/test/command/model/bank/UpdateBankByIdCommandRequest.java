package com.example.test.command.model.bank;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateBankByIdCommandRequest {

  @NotBlank
  private String companyGroupId;

  @NotBlank
  private String id;

  @NotBlank
  private String name;
}

package com.example.test.command.model.bank;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeleteBankByIdCommandRequest {

  @NotBlank
  private String companyGroupId;

  @NotBlank
  private String id;
}

package com.example.test.command.model.partner;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeletePartnerByIdCommandRequest {

  @NotBlank
  private String companyId;

  private String id;
}

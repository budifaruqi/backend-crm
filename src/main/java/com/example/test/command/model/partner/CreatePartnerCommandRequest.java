package com.example.test.command.model.partner;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreatePartnerCommandRequest {

  @NotBlank
  private String companyId;

  @NotBlank
  private String name;

  private String description;
}

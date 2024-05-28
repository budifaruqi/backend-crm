package com.example.test.command.model.lead;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetLeadByIdCommandRequest {

  @NotBlank
  private String companyId;

  private String id;
}

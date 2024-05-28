package com.example.test.command.model.potentialLead;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetPotentialLeadByIdCommandRequest {

  @NotBlank
  private String companyId;

  private String id;
}

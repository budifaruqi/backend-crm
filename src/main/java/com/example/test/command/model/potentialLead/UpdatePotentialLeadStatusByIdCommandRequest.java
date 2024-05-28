package com.example.test.command.model.potentialLead;

import com.example.test.common.enums.PotentialLeadStatus;
import com.example.test.validation.annotation.PotentialLeadStatusSubset;
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
public class UpdatePotentialLeadStatusByIdCommandRequest {

  @NotBlank
  private String companyId;

  @NotBlank
  private String id;

  @NotNull
  @PotentialLeadStatusSubset(anyOf = {PotentialLeadStatus.APPROVED, PotentialLeadStatus.CANCEL})
  private PotentialLeadStatus status;
}

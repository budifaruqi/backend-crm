package com.example.test.command.model.lead;

import com.example.test.common.enums.LeadStatus;
import com.example.test.validation.annotation.LeadStatusSubset;
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
public class UpdateLeadStatusByIdCommandRequest {

  @NotBlank
  private String companyGroupId;

  @NotBlank
  private String id;

  @NotNull
  @LeadStatusSubset
  private LeadStatus status;
}

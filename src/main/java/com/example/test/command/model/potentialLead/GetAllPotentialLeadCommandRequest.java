package com.example.test.command.model.potentialLead;

import com.example.test.common.enums.PotentialLeadStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetAllPotentialLeadCommandRequest {

  @NotBlank
  private String companyGroupId;

  private String name;

  private String partnerId;

  private PotentialLeadStatus status;

  private Pageable pageable;
}

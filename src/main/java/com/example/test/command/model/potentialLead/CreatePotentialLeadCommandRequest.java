package com.example.test.command.model.potentialLead;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreatePotentialLeadCommandRequest {

  @NotBlank
  private String companyGroupId;

  @NotBlank
  private String partnerId;

  @NotBlank
  private String name;

  private String phone;

  private String email;

  private List<String> requirementList;
}

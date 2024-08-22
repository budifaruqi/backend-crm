package com.example.test.web.model.response.potentialLead;

import com.example.test.common.enums.PotentialLeadStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetPotentialLeadWebResponse {

  private String id;

  private String companyGroupId;

  private String name;

  private String phone;

  private String email;

  private List<String> requirementList;

  private PotentialLeadStatus status;
}

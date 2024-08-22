package com.example.test.web.model.request.potentialLead;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdatePotentialLeadWebRequest {

  private String name;

  private String phone;

  private String email;

  private List<String> requirementList;
}

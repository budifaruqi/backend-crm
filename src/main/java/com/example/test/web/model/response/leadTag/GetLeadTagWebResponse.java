package com.example.test.web.model.response.leadTag;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetLeadTagWebResponse {

  private String id;

  private String companyGroupId;

  private String name;

  private String description;
}

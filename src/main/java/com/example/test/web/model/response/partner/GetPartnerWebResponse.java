package com.example.test.web.model.response.partner;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetPartnerWebResponse {

  private String id;

  private String companyId;

  private String name;

  private String description;
}

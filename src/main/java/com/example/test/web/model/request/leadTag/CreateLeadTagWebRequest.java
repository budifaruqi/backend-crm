package com.example.test.web.model.request.leadTag;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateLeadTagWebRequest {

  private String name;

  private String description;
}

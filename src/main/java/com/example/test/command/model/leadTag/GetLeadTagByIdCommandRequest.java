package com.example.test.command.model.leadTag;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetLeadTagByIdCommandRequest {

  @NotBlank
  private String companyGroupId;

  private String id;
}

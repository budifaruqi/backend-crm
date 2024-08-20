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
public class UpdateLeadTagByIdCommandRequest {

  @NotBlank
  private String companyGroupId;

  @NotBlank
  private String id;

  @NotBlank
  private String name;

  private String description;
}

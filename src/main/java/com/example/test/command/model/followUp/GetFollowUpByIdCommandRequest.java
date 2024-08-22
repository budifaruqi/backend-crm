package com.example.test.command.model.followUp;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetFollowUpByIdCommandRequest {

  @NotBlank
  private String companyGroupId;

  @NotBlank
  private String salesId;

  @NotBlank
  private String id;
}

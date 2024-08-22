package com.example.test.command.model.followUp;

import com.example.test.common.enums.FollowUpActivity;
import com.example.test.common.enums.FollowUpStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateFollowUpByIdCommandRequest {

  @NotBlank
  private String companyGroupId;

  @NotBlank
  private String id;

  @NotBlank
  private String leadId;

  @NotBlank
  private String salesId;

  @NotNull
  private FollowUpActivity activity;

  @NotNull
  private FollowUpStatus status;

  private String note;
}

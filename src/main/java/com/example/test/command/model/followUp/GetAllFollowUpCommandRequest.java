package com.example.test.command.model.followUp;

import com.example.test.common.enums.FollowUpActivity;
import com.example.test.common.enums.FollowUpStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetAllFollowUpCommandRequest {

  @NotBlank
  private String companyGroupId;

  @NotBlank
  private String salesId;

  private String leadId;

  private FollowUpActivity activity;

  private FollowUpStatus status;

  private Pageable pageable;
}

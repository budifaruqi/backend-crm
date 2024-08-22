package com.example.test.web.model.response.followUp;

import com.example.test.common.enums.FollowUpActivity;
import com.example.test.common.enums.FollowUpStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetFollowUpWebResponse {

  @Id
  private String id;

  private String companyGroupId;

  private String leadId;

  private String leadName;

  private String salesId;

  private FollowUpActivity activity;

  private FollowUpStatus status;

  private String note;
}

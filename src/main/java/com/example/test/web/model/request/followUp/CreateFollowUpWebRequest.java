package com.example.test.web.model.request.followUp;

import com.example.test.common.enums.FollowUpActivity;
import com.example.test.common.enums.FollowUpStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateFollowUpWebRequest {

  private String leadId;

  private FollowUpActivity activity;
  
  private FollowUpStatus status;

  private String note;
}

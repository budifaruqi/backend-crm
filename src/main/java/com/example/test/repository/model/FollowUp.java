package com.example.test.repository.model;

import com.example.test.common.constant.CollectionName;
import com.example.test.common.enums.FollowUpActivity;
import com.example.test.common.enums.FollowUpStatus;
import com.solusinegeri.data.model.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = CollectionName.FOLLOW_UP)
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FollowUp extends BaseEntity {

  @Id
  private String id;

  private String companyGroupId;

  private String leadId;

  private String salesId;

  private FollowUpActivity activity;

  private FollowUpStatus status;

  private String note;
}

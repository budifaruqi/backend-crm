package com.example.test.repository.model;

import com.example.test.common.constant.CollectionName;
import com.example.test.common.enums.PotentialLeadStatus;
import com.solusinegeri.data.model.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = CollectionName.POTENTIAL_LEAD)
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PotentialLead extends BaseEntity {

  @Id
  private String id;

  private String companyId;

  private String partnerId;

  private String name;

  private String phone;

  private String email;

  private List<String> requirementList;

  private PotentialLeadStatus status;
}

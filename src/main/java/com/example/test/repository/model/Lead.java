package com.example.test.repository.model;

import com.example.test.common.constant.CollectionName;
import com.example.test.common.enums.LeadStatus;
import com.solusinegeri.data.model.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = CollectionName.LEAD)
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Lead extends BaseEntity {

  @Id
  private String id;

  private String companyId;

  private String potentialLeadId;

  private String name;

  private String picName;

  private String picPhone;

  private String picEmail;

  private String description;

  private List<String> tags;

  private String partnerId;

  private String address;

  private String city;

  private String province;

  private String gMapLink;

  private Long potentialSize;

  private Long potentialRevenue;

  private String facebook;

  private String instagram;

  private String reference;

  private LeadStatus status;

  private Boolean isCustomer;

  private Boolean isLive;

  private Boolean isDormant;
}

package com.example.test.web.model.request.lead;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateLeadWebRequest {

  private String potentialLeadId;

  private String picName;

  private String picPhone;

  private String picEmail;

  private String description;

  private List<String> leadTagIds;

  private String address;

  private String city;

  private String province;

  private String gMapLink;

  private Long potentialSize;

  private Long potentialRevenue;

  private String facebook;

  private String instagram;

  private String reference;
}

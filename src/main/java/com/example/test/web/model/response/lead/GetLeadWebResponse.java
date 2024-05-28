package com.example.test.web.model.response.lead;

import com.example.test.common.enums.LeadStatus;
import com.example.test.common.vo.TagVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetLeadWebResponse {

  private String id;

  private String companyId;

  private String potentialLeadId;

  private String name;

  private String picName;

  private String picPhone;

  private String picEmail;

  private String description;

  private List<TagVO> tags;

  private String partnerId;

  private String partnerName;

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

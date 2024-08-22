package com.example.test.command.model.lead;

import com.example.test.common.constant.ErrorCode;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateLeadByIdCommandRequest {

  @NotBlank
  private String companyGroupId;

  @NotBlank
  private String id;

  @NotBlank
  private String picName;

  @NotBlank
  private String picPhone;

  @NotBlank
  private String picEmail;

  private String description;

  @NotBlank
  private String address;

  @NotBlank
  private String city;

  @NotBlank
  private String province;

  private String gMapLink;

  @NotNull
  @Min(value = 1, message = ErrorCode.VALUE_MUST_BE_GREATER_THAN_0)
  private Long potentialSize;

  @NotNull
  @Min(value = 1, message = ErrorCode.VALUE_MUST_BE_GREATER_THAN_0)
  private Long potentialRevenue;

  private String facebook;

  private String instagram;

  private List<String> tags;

  private String salesId;

  private String bankId;

  private String reference;
}

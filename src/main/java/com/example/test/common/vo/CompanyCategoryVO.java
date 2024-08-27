package com.example.test.common.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyCategoryVO {

  private String companyCategoryId;

  private String companyCategoryName;

  private List<String> features;
}

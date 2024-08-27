package com.example.test.client.model.response;

import com.example.test.common.vo.CompanyCategoryVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateExternalAccountClientResponse {

  private String _id;

  private String name;

  private String username;

  private String email;

  private String phone;

  private Boolean isActive;

  private List<CompanyCategoryVO> companyCategoryIds;
}

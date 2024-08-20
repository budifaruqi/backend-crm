package com.example.test.web.model.request.bank;

import com.example.test.common.enums.BankType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateBankWebRequest {

  private String name;

  private BankType type;

  private String parentId;
}

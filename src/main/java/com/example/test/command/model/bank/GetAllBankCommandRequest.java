package com.example.test.command.model.bank;

import com.example.test.common.enums.BankType;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetAllBankCommandRequest {

  @NotBlank
  private String companyGroupId;

  private String bankName;

  private BankType type;

  private Pageable pageable;
}

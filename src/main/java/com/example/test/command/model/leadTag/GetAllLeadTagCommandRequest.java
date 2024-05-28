package com.example.test.command.model.leadTag;

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
public class GetAllLeadTagCommandRequest {

  @NotBlank
  private String companyId;

  private String name;

  private Pageable pageable;
}

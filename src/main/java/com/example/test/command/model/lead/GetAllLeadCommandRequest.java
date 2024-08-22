package com.example.test.command.model.lead;

import com.example.test.common.enums.LeadStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetAllLeadCommandRequest {

  @NotBlank
  private String companyGroupId;

  private String leadName;

  private List<String> tagIds;

  private String city;

  private String province;

  private String reference;

  private LeadStatus status;

  private Pageable pageable;
}

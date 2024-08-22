package com.example.test.command.impl.potentialLead;

import com.example.test.command.model.potentialLead.GetPotentialLeadByIdCommandRequest;
import com.example.test.command.potentialLead.GetPotentialLeadByIdCommand;
import com.example.test.common.constant.ErrorCode;
import com.example.test.repository.PotentialLeadRepository;
import com.example.test.repository.model.PotentialLead;
import com.example.test.web.model.response.potentialLead.GetPotentialLeadWebResponse;
import com.solusinegeri.validation.model.exception.ValidationException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class GetPotentialLeadByIdCommandImpl implements GetPotentialLeadByIdCommand {

  private final PotentialLeadRepository potentialLeadRepository;

  public GetPotentialLeadByIdCommandImpl(PotentialLeadRepository potentialLeadRepository) {
    this.potentialLeadRepository = potentialLeadRepository;
  }

  @Override
  public Mono<GetPotentialLeadWebResponse> execute(GetPotentialLeadByIdCommandRequest request) {
    return Mono.defer(() -> findPotentialLead(request))
        .map(potentialLead -> toGetWebResponse(potentialLead));
  }

  private Mono<PotentialLead> findPotentialLead(GetPotentialLeadByIdCommandRequest request) {
    return potentialLeadRepository.findByDeletedFalseAndCompanyGroupIdAndId(request.getCompanyGroupId(),
            request.getId())
        .switchIfEmpty(Mono.error(new ValidationException(ErrorCode.POTENTIAL_LEAD_NOT_EXIST)));
  }

  private GetPotentialLeadWebResponse toGetWebResponse(PotentialLead potentialLead) {
    return GetPotentialLeadWebResponse.builder()
        .id(potentialLead.getId())
        .companyGroupId(potentialLead.getCompanyGroupId())
        .name(potentialLead.getName())
        .phone(potentialLead.getPhone())
        .email(potentialLead.getEmail())
        .requirementList(potentialLead.getRequirementList())
        .status(potentialLead.getStatus())
        .build();
  }
}

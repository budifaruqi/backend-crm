package com.example.test.command.impl.potentialLead;

import com.example.test.command.model.potentialLead.UpdatePotentialLeadStatusByIdCommandRequest;
import com.example.test.command.potentialLead.UpdatePotentialLeadStatusByIdCommand;
import com.example.test.common.constant.ErrorCode;
import com.example.test.common.enums.PotentialLeadStatus;
import com.example.test.repository.PotentialLeadRepository;
import com.example.test.repository.model.PotentialLead;
import com.example.test.web.model.response.potentialLead.GetPotentialLeadWebResponse;
import com.solusinegeri.validation.model.exception.ValidationException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UpdatePotentialLeadStatusByIdCommandImpl implements UpdatePotentialLeadStatusByIdCommand {

  private final PotentialLeadRepository potentialLeadRepository;

  public UpdatePotentialLeadStatusByIdCommandImpl(PotentialLeadRepository potentialLeadRepository) {
    this.potentialLeadRepository = potentialLeadRepository;
  }

  @Override
  public Mono<GetPotentialLeadWebResponse> execute(UpdatePotentialLeadStatusByIdCommandRequest request) {
    return Mono.defer(() -> findPotentialLead(request))
        .map(potentialLead -> updatePotentialLead(potentialLead, request))
        .flatMap(potentialLeadRepository::save)
        .map(this::toGetWebResponse);
  }

  private Mono<PotentialLead> findPotentialLead(UpdatePotentialLeadStatusByIdCommandRequest request) {
    return potentialLeadRepository.findByDeletedFalseAndCompanyIdAndId(request.getCompanyId(), request.getId())
        .switchIfEmpty(Mono.error(new ValidationException(ErrorCode.POTENTIAL_LEAD_NOT_EXIST)))
        .filter(s -> s.getStatus() == PotentialLeadStatus.PENDING)
        .switchIfEmpty(Mono.error(new ValidationException(ErrorCode.STATUS_NOT_VALID)));
  }

  private PotentialLead updatePotentialLead(PotentialLead potentialLead,
      UpdatePotentialLeadStatusByIdCommandRequest request) {
    potentialLead.setStatus(request.getStatus());

    return potentialLead;
  }

  private GetPotentialLeadWebResponse toGetWebResponse(PotentialLead potentialLead) {
    return GetPotentialLeadWebResponse.builder()
        .id(potentialLead.getId())
        .companyId(potentialLead.getCompanyId())
        .partnerId(potentialLead.getPartnerId())
        .name(potentialLead.getName())
        .phone(potentialLead.getPhone())
        .email(potentialLead.getEmail())
        .requirementList(potentialLead.getRequirementList())
        .status(potentialLead.getStatus())
        .build();
  }
}

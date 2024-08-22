package com.example.test.command.impl.potentialLead;

import com.example.test.command.model.potentialLead.UpdatePotentialLeadCommandRequest;
import com.example.test.command.potentialLead.UpdatePotentialLeadCommand;
import com.example.test.common.constant.ErrorCode;
import com.example.test.repository.PotentialLeadRepository;
import com.example.test.repository.model.PotentialLead;
import com.example.test.web.model.response.potentialLead.GetPotentialLeadWebResponse;
import com.solusinegeri.validation.model.exception.ValidationException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
public class UpdatePotentialLeadCommandImpl implements UpdatePotentialLeadCommand {

  private final PotentialLeadRepository potentialLeadRepository;

  public UpdatePotentialLeadCommandImpl(PotentialLeadRepository potentialLeadRepository) {
    this.potentialLeadRepository = potentialLeadRepository;
  }

  @Override
  public Mono<GetPotentialLeadWebResponse> execute(UpdatePotentialLeadCommandRequest request) {
    return Mono.defer(() -> findPotentialLead(request))
        .flatMap(potentialLead -> Mono.defer(() -> checkRequest(request))
            .map(s -> updatePotentialLead(potentialLead, request))
            .flatMap(potentialLeadRepository::save))
        .map(this::toGetWebResponse);
  }

  private Mono<PotentialLead> findPotentialLead(UpdatePotentialLeadCommandRequest request) {
    return potentialLeadRepository.findByDeletedFalseAndCompanyGroupIdAndId(request.getCompanyGroupId(),
            request.getId())
        .switchIfEmpty(Mono.error(new ValidationException(ErrorCode.POTENTIAL_LEAD_NOT_EXIST)));
  }

  private Mono<PotentialLead> checkRequest(UpdatePotentialLeadCommandRequest request) {
    return potentialLeadRepository.findByDeletedFalseAndCompanyGroupIdAndName(request.getCompanyGroupId(),
            request.getName())
        .switchIfEmpty(Mono.fromSupplier(() -> PotentialLead.builder()
            .build()))
        .filter(s -> s.getId() == null || Objects.equals(s.getId(), request.getId()))
        .switchIfEmpty(Mono.error(new ValidationException(ErrorCode.NAME_ALREADY_USED)));
  }

  private PotentialLead updatePotentialLead(PotentialLead potentialLead, UpdatePotentialLeadCommandRequest request) {
    potentialLead.setName(request.getName());
    potentialLead.setPhone(request.getPhone());
    potentialLead.setEmail(request.getEmail());
    potentialLead.setRequirementList(request.getRequirementList());

    return potentialLead;
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

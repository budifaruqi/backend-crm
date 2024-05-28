package com.example.test.command.impl.potentialLead;

import com.example.test.command.model.potentialLead.CreatePotentialLeadCommandRequest;
import com.example.test.command.potentialLead.CreatePotentialLeadCommand;
import com.example.test.common.constant.ErrorCode;
import com.example.test.common.enums.PotentialLeadStatus;
import com.example.test.repository.PotentialLeadRepository;
import com.example.test.repository.model.PotentialLead;
import com.solusinegeri.validation.model.exception.ValidationException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
public class CreatePotentialLeadCommandImpl implements CreatePotentialLeadCommand {

  private final PotentialLeadRepository potentialLeadRepository;

  public CreatePotentialLeadCommandImpl(PotentialLeadRepository potentialLeadRepository) {
    this.potentialLeadRepository = potentialLeadRepository;
  }

  @Override
  public Mono<Object> execute(CreatePotentialLeadCommandRequest request) {
    return Mono.defer(() -> checkName(request))
        .flatMap(s -> checkPartner(request))
        .map(s -> toPotentialLead(request))
        .flatMap(potentialLeadRepository::save);
  }

  private Mono<PotentialLead> checkName(CreatePotentialLeadCommandRequest request) {
    return potentialLeadRepository.findByDeletedFalseAndCompanyIdAndName(request.getCompanyId(), request.getName())
        .switchIfEmpty(Mono.fromSupplier(() -> PotentialLead.builder()
            .build()))
        .filter(s -> !Objects.equals(s.getName(), request.getName()))
        .switchIfEmpty(Mono.error(new ValidationException(ErrorCode.NAME_ALREADY_USED)));
  }

  private Mono<PotentialLead> checkPartner(CreatePotentialLeadCommandRequest request) {
    return potentialLeadRepository.findByDeletedFalseAndCompanyIdAndPartnerId(request.getCompanyId(),
            request.getPartnerId())
        .switchIfEmpty(Mono.fromSupplier(() -> PotentialLead.builder()
            .build()))
        .filter(s -> s.getStatus() == PotentialLeadStatus.CANCEL ||
            !Objects.equals(s.getPartnerId(), request.getPartnerId()))
        .switchIfEmpty(Mono.error(new ValidationException(ErrorCode.PARTNER_ALREADY_USED)));
  }

  private PotentialLead toPotentialLead(CreatePotentialLeadCommandRequest request) {
    return PotentialLead.builder()
        .companyId(request.getCompanyId())
        .partnerId(request.getPartnerId())
        .name(request.getName())
        .phone(request.getPhone())
        .email(request.getEmail())
        .requirementList(request.getRequirementList())
        .status(PotentialLeadStatus.PENDING)
        .build();
  }
}

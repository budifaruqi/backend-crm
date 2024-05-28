package com.example.test.command.impl.potentialLead;

import com.example.test.command.model.potentialLead.GetPotentialLeadByIdCommandRequest;
import com.example.test.command.potentialLead.GetPotentialLeadByIdCommand;
import com.example.test.common.constant.ErrorCode;
import com.example.test.repository.PartnerRepository;
import com.example.test.repository.PotentialLeadRepository;
import com.example.test.repository.model.Partner;
import com.example.test.repository.model.PotentialLead;
import com.example.test.web.model.response.potentialLead.GetPotentialLeadWebResponse;
import com.solusinegeri.validation.model.exception.ValidationException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class GetPotentialLeadByIdCommandImpl implements GetPotentialLeadByIdCommand {

  private final PotentialLeadRepository potentialLeadRepository;

  private final PartnerRepository partnerRepository;

  public GetPotentialLeadByIdCommandImpl(PotentialLeadRepository potentialLeadRepository,
      PartnerRepository partnerRepository) {
    this.potentialLeadRepository = potentialLeadRepository;
    this.partnerRepository = partnerRepository;
  }

  @Override
  public Mono<GetPotentialLeadWebResponse> execute(GetPotentialLeadByIdCommandRequest request) {
    return Mono.defer(() -> findPotentialLead(request))
        .flatMap(potentialLead -> Mono.defer(() -> getPartner(potentialLead))
            .map(partner -> toGetWebResponse(potentialLead, partner)));
  }

  private Mono<PotentialLead> findPotentialLead(GetPotentialLeadByIdCommandRequest request) {
    return potentialLeadRepository.findByDeletedFalseAndCompanyIdAndId(request.getCompanyId(), request.getId())
        .switchIfEmpty(Mono.error(new ValidationException(ErrorCode.POTENTIAL_LEAD_NOT_EXIST)));
  }

  private Mono<Partner> getPartner(PotentialLead potentialLead) {
    return partnerRepository.findByDeletedFalseAndCompanyIdAndId(potentialLead.getCompanyId(),
            potentialLead.getPartnerId())
        .switchIfEmpty(Mono.fromSupplier(() -> Partner.builder()
            .name(ErrorCode.PARTNER_NOT_EXIST)
            .build()));
  }

  private GetPotentialLeadWebResponse toGetWebResponse(PotentialLead potentialLead, Partner partner) {
    return GetPotentialLeadWebResponse.builder()
        .id(potentialLead.getId())
        .companyId(potentialLead.getCompanyId())
        .partnerId(potentialLead.getPartnerId())
        .partnerName(partner.getName())
        .name(potentialLead.getName())
        .phone(potentialLead.getPhone())
        .email(potentialLead.getEmail())
        .requirementList(potentialLead.getRequirementList())
        .status(potentialLead.getStatus())
        .build();
  }
}

package com.example.test.command.impl.potentialLead;

import com.example.test.command.model.potentialLead.GetAllPotentialLeadCommandRequest;
import com.example.test.command.potentialLead.GetAllPotentialLeadCommand;
import com.example.test.common.constant.ErrorCode;
import com.example.test.repository.PartnerRepository;
import com.example.test.repository.PotentialLeadRepository;
import com.example.test.repository.model.Partner;
import com.example.test.repository.model.PotentialLead;
import com.example.test.web.model.response.potentialLead.GetPotentialLeadWebResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class GetAllPotentialLeadCommandImpl implements GetAllPotentialLeadCommand {

  private final PotentialLeadRepository potentialLeadRepository;

  private final PartnerRepository partnerRepository;

  public GetAllPotentialLeadCommandImpl(PotentialLeadRepository potentialLeadRepository,
      PartnerRepository partnerRepository) {
    this.potentialLeadRepository = potentialLeadRepository;
    this.partnerRepository = partnerRepository;
  }

  @Override
  public Mono<Page<GetPotentialLeadWebResponse>> execute(GetAllPotentialLeadCommandRequest request) {
    return Mono.zip(getData(request), count(request))
        .map(objects -> toPageResponse(request, objects.getT1(), objects.getT2()));
  }

  private Mono<Long> count(GetAllPotentialLeadCommandRequest request) {
    return potentialLeadRepository.countAllByDeletedFalseAndFilter(request.getCompanyId(), request.getName(),
        request.getPartnerId(), request.getStatus(), request.getPageable());
  }

  private Mono<List<GetPotentialLeadWebResponse>> getData(GetAllPotentialLeadCommandRequest request) {
    return Flux.defer(() -> getPotentialLead(request))
        .flatMapSequential(potentialLead -> Mono.defer(() -> getPartner(potentialLead))
            .map(partner -> toGetWebResponse(potentialLead, partner)))
        .collectList();
  }

  private Mono<Partner> getPartner(PotentialLead potentialLead) {
    return partnerRepository.findByDeletedFalseAndCompanyIdAndId(potentialLead.getCompanyId(),
            potentialLead.getPartnerId())
        .switchIfEmpty(Mono.fromSupplier(() -> Partner.builder()
            .name(ErrorCode.PARTNER_NOT_EXIST)
            .build()));
  }

  private Flux<PotentialLead> getPotentialLead(GetAllPotentialLeadCommandRequest request) {
    return potentialLeadRepository.findAllByDeletedFalseAndFilter(request.getCompanyId(), request.getName(),
        request.getPartnerId(), request.getStatus(), request.getPageable());
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

  private PageImpl<GetPotentialLeadWebResponse> toPageResponse(GetAllPotentialLeadCommandRequest request,
      List<GetPotentialLeadWebResponse> contents, Long total) {
    return new PageImpl<>(contents, request.getPageable(), total);
  }
}

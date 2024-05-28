package com.example.test.command.impl.partner;

import com.example.test.command.model.partner.GetAllPartnerCommandRequest;
import com.example.test.command.partner.GetAllPartnerCommand;
import com.example.test.repository.PartnerRepository;
import com.example.test.repository.model.Partner;
import com.example.test.web.model.response.partner.GetPartnerWebResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class GetAllPartnerCommandImpl implements GetAllPartnerCommand {

  private final PartnerRepository partnerRepository;

  public GetAllPartnerCommandImpl(PartnerRepository partnerRepository) {
    this.partnerRepository = partnerRepository;
  }

  @Override
  public Mono<Page<GetPartnerWebResponse>> execute(GetAllPartnerCommandRequest request) {
    return Mono.zip(getData(request), count(request))
        .map(objects -> toPageResponse(request, objects.getT1(), objects.getT2()));
  }

  private Mono<Long> count(GetAllPartnerCommandRequest request) {
    return partnerRepository.countAllByDeletedFalseAndFilter(request.getCompanyId(), request.getName(),
        request.getPageable());
  }

  private Mono<List<GetPartnerWebResponse>> getData(GetAllPartnerCommandRequest request) {
    return Flux.defer(() -> getPotentialLead(request))
        .map(this::toGetWebResponse)
        .collectList();
  }

  private Flux<Partner> getPotentialLead(GetAllPartnerCommandRequest request) {
    return partnerRepository.findAllByDeletedFalseAndFilter(request.getCompanyId(), request.getName(),
        request.getPageable());
  }

  private GetPartnerWebResponse toGetWebResponse(Partner leadTag) {
    return GetPartnerWebResponse.builder()
        .id(leadTag.getId())
        .companyId(leadTag.getCompanyId())
        .name(leadTag.getName())
        .description(leadTag.getDescription())
        .build();
  }

  private PageImpl<GetPartnerWebResponse> toPageResponse(GetAllPartnerCommandRequest request,
      List<GetPartnerWebResponse> contents, Long total) {
    return new PageImpl<>(contents, request.getPageable(), total);
  }
}

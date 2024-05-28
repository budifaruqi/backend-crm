package com.example.test.command.impl.leadTag;

import com.example.test.command.leadTag.GetAllLeadTagCommand;
import com.example.test.command.model.leadTag.GetAllLeadTagCommandRequest;
import com.example.test.repository.LeadTagRepository;
import com.example.test.repository.model.LeadTag;
import com.example.test.web.model.response.leadTag.GetLeadTagWebResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class GetAllLeadTagCommandImpl implements GetAllLeadTagCommand {

  private final LeadTagRepository leadTagRepository;

  public GetAllLeadTagCommandImpl(LeadTagRepository leadTagRepository) {
    this.leadTagRepository = leadTagRepository;
  }

  @Override
  public Mono<Page<GetLeadTagWebResponse>> execute(GetAllLeadTagCommandRequest request) {
    return Mono.zip(getData(request), count(request))
        .map(objects -> toPageResponse(request, objects.getT1(), objects.getT2()));
  }

  private Mono<Long> count(GetAllLeadTagCommandRequest request) {
    return leadTagRepository.countAllByDeletedFalseAndFilter(request.getCompanyId(), request.getName(),
        request.getPageable());
  }

  private Mono<List<GetLeadTagWebResponse>> getData(GetAllLeadTagCommandRequest request) {
    return Flux.defer(() -> getPotentialLead(request))
        .map(this::toGetWebResponse)
        .collectList();
  }

  private Flux<LeadTag> getPotentialLead(GetAllLeadTagCommandRequest request) {
    return leadTagRepository.findAllByDeletedFalseAndFilter(request.getCompanyId(), request.getName(),
        request.getPageable());
  }

  private GetLeadTagWebResponse toGetWebResponse(LeadTag leadTag) {
    return GetLeadTagWebResponse.builder()
        .id(leadTag.getId())
        .companyId(leadTag.getCompanyId())
        .name(leadTag.getName())
        .description(leadTag.getDescription())
        .build();
  }

  private PageImpl<GetLeadTagWebResponse> toPageResponse(GetAllLeadTagCommandRequest request,
      List<GetLeadTagWebResponse> contents, Long total) {
    return new PageImpl<>(contents, request.getPageable(), total);
  }
}

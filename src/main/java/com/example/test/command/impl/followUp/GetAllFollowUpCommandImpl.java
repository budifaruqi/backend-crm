package com.example.test.command.impl.followUp;

import com.example.test.command.followUp.GetAllFollowUpCommand;
import com.example.test.command.model.followUp.GetAllFollowUpCommandRequest;
import com.example.test.common.constant.ErrorCode;
import com.example.test.repository.FollowUpRepository;
import com.example.test.repository.LeadRepository;
import com.example.test.repository.model.FollowUp;
import com.example.test.repository.model.Lead;
import com.example.test.web.model.response.followUp.GetFollowUpWebResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class GetAllFollowUpCommandImpl implements GetAllFollowUpCommand {

  private final LeadRepository leadRepository;

  private final FollowUpRepository followUpRepository;

  public GetAllFollowUpCommandImpl(LeadRepository leadRepository, FollowUpRepository followUpRepository) {
    this.leadRepository = leadRepository;
    this.followUpRepository = followUpRepository;
  }

  @Override
  public Mono<Page<GetFollowUpWebResponse>> execute(GetAllFollowUpCommandRequest request) {
    return Mono.zip(getData(request), count(request))
        .map(objects -> toPageResponse(request, objects.getT1(), objects.getT2()));
  }

  private Mono<Long> count(GetAllFollowUpCommandRequest request) {
    return followUpRepository.countAllByDeletedFalseAndFilter(request.getCompanyGroupId(), request.getSalesId(),
        request.getLeadId(), request.getActivity(), request.getStatus(), request.getPageable());
  }

  private Mono<List<GetFollowUpWebResponse>> getData(GetAllFollowUpCommandRequest request) {
    return Flux.defer(() -> getFollowUp(request))
        .flatMapSequential(followUp -> Mono.defer(() -> findLead(followUp))
            .map(lead -> toGetWebResponse(lead, followUp)))
        .collectList();
  }

  private Flux<FollowUp> getFollowUp(GetAllFollowUpCommandRequest request) {
    return followUpRepository.findAllByDeletedFalseAndFilter(request.getCompanyGroupId(), request.getSalesId(),
        request.getLeadId(), request.getActivity(), request.getStatus(), request.getPageable());
  }

  private Mono<Lead> findLead(FollowUp followUp) {
    return leadRepository.findByDeletedFalseAndCompanyGroupIdAndId(followUp.getCompanyGroupId(), followUp.getLeadId())
        .switchIfEmpty(Mono.fromSupplier(() -> Lead.builder()
            .name(ErrorCode.LEAD_NOT_EXIST)
            .build()));
  }

  private GetFollowUpWebResponse toGetWebResponse(Lead lead, FollowUp followUp) {
    return GetFollowUpWebResponse.builder()
        .id(followUp.getId())
        .companyGroupId(followUp.getCompanyGroupId())
        .leadId(lead.getId())
        .leadName(lead.getName())
        .salesId(followUp.getSalesId())
        .activity(followUp.getActivity())
        .status(followUp.getStatus())
        .note(followUp.getNote())
        .build();
  }

  private PageImpl<GetFollowUpWebResponse> toPageResponse(GetAllFollowUpCommandRequest request,
      List<GetFollowUpWebResponse> contents, Long total) {
    return new PageImpl<>(contents, request.getPageable(), total);
  }
}

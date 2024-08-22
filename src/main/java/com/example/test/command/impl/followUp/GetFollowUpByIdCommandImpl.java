package com.example.test.command.impl.followUp;

import com.example.test.command.followUp.GetFollowUpByIdCommand;
import com.example.test.command.model.followUp.GetFollowUpByIdCommandRequest;
import com.example.test.common.constant.ErrorCode;
import com.example.test.common.helper.response.exception.MicroserviceValidationException;
import com.example.test.repository.FollowUpRepository;
import com.example.test.repository.LeadRepository;
import com.example.test.repository.model.FollowUp;
import com.example.test.repository.model.Lead;
import com.example.test.web.model.response.followUp.GetFollowUpWebResponse;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class GetFollowUpByIdCommandImpl implements GetFollowUpByIdCommand {

  private final FollowUpRepository followUpRepository;

  private final LeadRepository leadRepository;

  public GetFollowUpByIdCommandImpl(FollowUpRepository followUpRepository, LeadRepository leadRepository) {
    this.followUpRepository = followUpRepository;
    this.leadRepository = leadRepository;
  }

  @Override
  public Mono<GetFollowUpWebResponse> execute(GetFollowUpByIdCommandRequest request) {
    return Mono.defer(() -> findFollowUp(request))
        .flatMap(followUp -> Mono.defer(() -> findLead(followUp))
            .map(lead -> toGetWebResponse(lead, followUp)));
  }

  private Mono<FollowUp> findFollowUp(GetFollowUpByIdCommandRequest request) {
    return followUpRepository.findByDeletedFalseAndCompanyGroupIdAndSalesIdAndId(request.getCompanyGroupId(),
            request.getSalesId(), request.getId())
        .switchIfEmpty(Mono.error(new MicroserviceValidationException(ErrorCode.FOLLOW_UP_NOT_EXIST)));
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
        .activity(followUp.getActivity())
        .salesId(followUp.getSalesId())
        .status(followUp.getStatus())
        .note(followUp.getNote())
        .build();
  }
}

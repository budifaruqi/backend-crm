package com.example.test.command.impl.followUp;

import com.example.test.command.followUp.CreateFollowUpCommand;
import com.example.test.command.model.followUp.CreateFollowUpCommandRequest;
import com.example.test.common.constant.ErrorCode;
import com.example.test.common.enums.LeadStatus;
import com.example.test.common.helper.response.exception.MicroserviceValidationException;
import com.example.test.repository.FollowUpRepository;
import com.example.test.repository.LeadRepository;
import com.example.test.repository.model.FollowUp;
import com.example.test.repository.model.Lead;
import com.example.test.web.model.response.followUp.GetFollowUpWebResponse;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CreateFollowUpCommandImpl implements CreateFollowUpCommand {

  private final FollowUpRepository followUpRepository;

  private final LeadRepository leadRepository;

  public CreateFollowUpCommandImpl(FollowUpRepository followUpRepository, LeadRepository leadRepository) {
    this.followUpRepository = followUpRepository;
    this.leadRepository = leadRepository;
  }

  @Override
  public Mono<Object> execute(CreateFollowUpCommandRequest request) {
    return Mono.defer(() -> findLead(request))
        .flatMap(lead -> Mono.fromSupplier(() -> toFollowUp(lead, request))
            .flatMap(followUpRepository::save)
            .flatMap(followUp -> Mono.fromSupplier(() -> updateLead(lead, followUp))
                .flatMap(leadRepository::save)
                .map(s -> toGetWebResponse(followUp, s))));
  }

  private Mono<Lead> findLead(CreateFollowUpCommandRequest request) {
    return leadRepository.findByDeletedFalseAndCompanyGroupIdAndId(request.getCompanyGroupId(), request.getLeadId())
        .switchIfEmpty(Mono.error(new MicroserviceValidationException(ErrorCode.LEAD_NOT_EXIST)));
  }

  private FollowUp toFollowUp(Lead lead, CreateFollowUpCommandRequest request) {
    return FollowUp.builder()
        .companyGroupId(request.getCompanyGroupId())
        .leadId(lead.getId())
        .salesId(request.getSalesId())
        .status(request.getStatus())
        .note(request.getNote())
        .build();
  }

  private Lead updateLead(Lead lead, FollowUp followUp) {
    switch (followUp.getStatus()) {
      case PRESENTATION_REQUEST:
      case FOLLOW_UP:
      case PROPOSAL_SENT:
      case NO_RESPONSE:
      case FOLLOW_UP_NEEDED:
      case PRESENTATION:
      case POST_PRESENTATION:
        lead.setStatus(LeadStatus.PENDING);
        break;
      case NOT_INTERESTED:
        lead.setStatus(LeadStatus.LOST);
        break;
      case QUOTATION:
      case INTERESTED:
        lead.setStatus(LeadStatus.WON);
        // Add more cases as needed
      default:
        break;
    }

    return lead;
  }

  private GetFollowUpWebResponse toGetWebResponse(FollowUp followUp, Lead s) {
    return GetFollowUpWebResponse.builder()
        .id(followUp.getId())
        .companyGroupId(followUp.getCompanyGroupId())
        .leadId(followUp.getLeadId())
        .leadName(s.getName())
        .salesId(followUp.getSalesId())
        .activity(followUp.getActivity())
        .status(followUp.getStatus())
        .note(followUp.getNote())
        .build();
  }
}

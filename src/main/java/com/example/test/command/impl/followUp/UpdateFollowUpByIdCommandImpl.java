package com.example.test.command.impl.followUp;

import com.example.test.command.followUp.UpdateFollowUpByIdCommand;
import com.example.test.command.model.followUp.UpdateFollowUpByIdCommandRequest;
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
public class UpdateFollowUpByIdCommandImpl implements UpdateFollowUpByIdCommand {

  private final LeadRepository leadRepository;

  private final FollowUpRepository followUpRepository;

  public UpdateFollowUpByIdCommandImpl(LeadRepository leadRepository, FollowUpRepository followUpRepository) {
    this.leadRepository = leadRepository;
    this.followUpRepository = followUpRepository;
  }

  @Override
  public Mono<Object> execute(UpdateFollowUpByIdCommandRequest request) {
    return Mono.defer(() -> findFollowUp(request))
        .map(followUp -> updateFollowUp(followUp, request))
        .flatMap(followUpRepository::save)
        .flatMap(followUp -> Mono.defer(() -> findLead(followUp))
            .map(lead -> updateLead(lead, followUp))
            .flatMap(leadRepository::save)
            .map(s -> toGetWebResponse(followUp, s)));
  }

  private Mono<FollowUp> findFollowUp(UpdateFollowUpByIdCommandRequest request) {
    return followUpRepository.findByDeletedFalseAndCompanyGroupIdAndSalesIdAndId(request.getCompanyGroupId(),
            request.getSalesId(), request.getId())
        .switchIfEmpty(Mono.error(new MicroserviceValidationException(ErrorCode.FOLLOW_UP_NOT_EXIST)));
  }

  private FollowUp updateFollowUp(FollowUp followUp, UpdateFollowUpByIdCommandRequest request) {
    followUp.setLeadId(request.getLeadId());
    followUp.setStatus(request.getStatus());
    followUp.setNote(request.getNote());

    return followUp;
  }

  private Mono<Lead> findLead(FollowUp request) {
    return leadRepository.findByDeletedFalseAndCompanyGroupIdAndId(request.getCompanyGroupId(), request.getLeadId())
        .switchIfEmpty(Mono.error(new MicroserviceValidationException(ErrorCode.LEAD_NOT_EXIST)));
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

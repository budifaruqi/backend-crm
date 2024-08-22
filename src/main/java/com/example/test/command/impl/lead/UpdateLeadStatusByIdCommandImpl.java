package com.example.test.command.impl.lead;

import com.example.test.command.lead.UpdateLeadStatusByIdCommand;
import com.example.test.command.model.lead.UpdateLeadStatusByIdCommandRequest;
import com.example.test.common.constant.ErrorCode;
import com.example.test.common.helper.response.exception.MicroserviceValidationException;
import com.example.test.repository.LeadRepository;
import com.example.test.repository.model.Lead;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UpdateLeadStatusByIdCommandImpl implements UpdateLeadStatusByIdCommand {

  private final LeadRepository leadRepository;

  public UpdateLeadStatusByIdCommandImpl(LeadRepository leadRepository) {
    this.leadRepository = leadRepository;
  }

  @Override
  public Mono<Object> execute(UpdateLeadStatusByIdCommandRequest request) {
    return Mono.defer(() -> findLead(request))
        .map(lead -> updateStatus(lead, request))
        .flatMap(leadRepository::save);
  }

  private Mono<Lead> findLead(UpdateLeadStatusByIdCommandRequest request) {
    return leadRepository.findByDeletedFalseAndCompanyGroupIdAndId(request.getCompanyGroupId(), request.getId())
        .switchIfEmpty(Mono.error(new MicroserviceValidationException(ErrorCode.LEAD_NOT_EXIST)));
  }

  private Lead updateStatus(Lead lead, UpdateLeadStatusByIdCommandRequest request) {
    lead.setStatus(request.getStatus());

    return lead;
  }
}

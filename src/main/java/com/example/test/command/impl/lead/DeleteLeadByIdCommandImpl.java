package com.example.test.command.impl.lead;

import com.example.test.command.lead.DeleteLeadByIdCommand;
import com.example.test.command.model.lead.DeleteLeadByIdCommandRequest;
import com.example.test.common.constant.ErrorCode;
import com.example.test.common.helper.response.exception.MicroserviceValidationException;
import com.example.test.repository.LeadRepository;
import com.example.test.repository.model.Lead;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class DeleteLeadByIdCommandImpl implements DeleteLeadByIdCommand {

  private final LeadRepository leadRepository;

  public DeleteLeadByIdCommandImpl(LeadRepository leadRepository) {
    this.leadRepository = leadRepository;
  }

  @Override
  public Mono<Void> execute(DeleteLeadByIdCommandRequest request) {
    return Mono.defer(() -> findLead(request))
        .map(this::deleteLead)
        .flatMap(leadRepository::save)
        .then();
  }

  private Mono<Lead> findLead(DeleteLeadByIdCommandRequest request) {
    return leadRepository.findByDeletedFalseAndCompanyGroupIdAndId(request.getCompanyGroupId(), request.getId())
        .switchIfEmpty(Mono.error(new MicroserviceValidationException(ErrorCode.LEAD_NOT_EXIST)));
  }

  private Lead deleteLead(Lead lead) {
    lead.setDeleted(true);

    return lead;
  }
}

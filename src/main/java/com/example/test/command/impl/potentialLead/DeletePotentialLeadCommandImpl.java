package com.example.test.command.impl.potentialLead;

import com.example.test.command.model.potentialLead.DeletePotentialLeadCommandRequest;
import com.example.test.command.potentialLead.DeletePotentialLeadCommand;
import com.example.test.common.constant.ErrorCode;
import com.example.test.repository.PotentialLeadRepository;
import com.example.test.repository.model.PotentialLead;
import com.solusinegeri.validation.model.exception.ValidationException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class DeletePotentialLeadCommandImpl implements DeletePotentialLeadCommand {

  private final PotentialLeadRepository potentialLeadRepository;

  public DeletePotentialLeadCommandImpl(PotentialLeadRepository potentialLeadRepository) {
    this.potentialLeadRepository = potentialLeadRepository;
  }

  @Override
  public Mono<Object> execute(DeletePotentialLeadCommandRequest request) {
    return Mono.defer(() -> findLead(request))
        .map(this::deleteLead)
        .flatMap(potentialLeadRepository::save);
  }

  private Mono<PotentialLead> findLead(DeletePotentialLeadCommandRequest request) {
    return potentialLeadRepository.findByDeletedFalseAndCompanyGroupIdAndId(request.getCompanyGroupId(),
            request.getId())
        .switchIfEmpty(Mono.error(new ValidationException(ErrorCode.POTENTIAL_LEAD_NOT_EXIST)));
  }

  private PotentialLead deleteLead(PotentialLead potentialLead) {
    potentialLead.setDeleted(true);

    return potentialLead;
  }
}

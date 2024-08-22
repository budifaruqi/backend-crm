package com.example.test.command.impl.followUp;

import com.example.test.command.followUp.DeleteFollowUpByIdCommand;
import com.example.test.command.model.followUp.DeleteFollowUpByIdCommandRequest;
import com.example.test.common.constant.ErrorCode;
import com.example.test.common.helper.response.exception.MicroserviceValidationException;
import com.example.test.repository.FollowUpRepository;
import com.example.test.repository.model.FollowUp;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class DeleteFollowUpByIdCommandImpl implements DeleteFollowUpByIdCommand {

  private final FollowUpRepository followUpRepository;

  public DeleteFollowUpByIdCommandImpl(FollowUpRepository followUpRepository) {
    this.followUpRepository = followUpRepository;
  }

  @Override
  public Mono<Void> execute(DeleteFollowUpByIdCommandRequest request) {
    return Mono.defer(() -> findFollowUp(request))
        .map(this::deleteFollowUp)
        .flatMap(followUpRepository::save)
        .then();
  }

  private Mono<FollowUp> findFollowUp(DeleteFollowUpByIdCommandRequest request) {
    return followUpRepository.findByDeletedFalseAndCompanyGroupIdAndSalesIdAndId(request.getCompanyGroupId(),
            request.getSalesId(), request.getId())
        .switchIfEmpty(Mono.error(new MicroserviceValidationException(ErrorCode.FOLLOW_UP_NOT_EXIST)));
  }

  private FollowUp deleteFollowUp(FollowUp followUp) {
    followUp.setDeleted(true);

    return followUp;
  }
}

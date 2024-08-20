package com.example.test.command.impl.leadTag;

import com.example.test.command.leadTag.DeleteLeadTagByIdCommand;
import com.example.test.command.model.leadTag.DeleteLeadTagByIdCommandRequest;
import com.example.test.common.constant.ErrorCode;
import com.example.test.common.helper.response.exception.MicroserviceValidationException;
import com.example.test.repository.LeadTagRepository;
import com.example.test.repository.model.LeadTag;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class DeleteLeadTagByIdCommandImpl implements DeleteLeadTagByIdCommand {

  private final LeadTagRepository leadTagRepository;

  public DeleteLeadTagByIdCommandImpl(LeadTagRepository leadTagRepository) {
    this.leadTagRepository = leadTagRepository;
  }

  @Override
  public Mono<Void> execute(DeleteLeadTagByIdCommandRequest request) {
    return Mono.defer(() -> findLeadTag(request))
        .map(this::deleteTag)
        .flatMap(leadTagRepository::save)
        .then();
  }

  private Mono<LeadTag> findLeadTag(DeleteLeadTagByIdCommandRequest request) {
    return leadTagRepository.findByDeletedFalseAndCompanyGroupIdAndId(request.getCompanyGroupId(), request.getId())
        .switchIfEmpty(Mono.error(new MicroserviceValidationException(ErrorCode.TAG_NOT_EXIST)));
  }

  private LeadTag deleteTag(LeadTag leadTag) {
    leadTag.setDeleted(true);

    return leadTag;
  }
}

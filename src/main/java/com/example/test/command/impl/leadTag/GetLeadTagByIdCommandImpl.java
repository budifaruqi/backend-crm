package com.example.test.command.impl.leadTag;

import com.example.test.command.leadTag.GetLeadTagByIdCommand;
import com.example.test.command.model.leadTag.GetLeadTagByIdCommandRequest;
import com.example.test.common.constant.ErrorCode;
import com.example.test.common.helper.response.exception.MicroserviceValidationException;
import com.example.test.repository.LeadTagRepository;
import com.example.test.repository.model.LeadTag;
import com.example.test.web.model.response.leadTag.GetLeadTagWebResponse;
import com.solusinegeri.validation.model.exception.ValidationException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class GetLeadTagByIdCommandImpl implements GetLeadTagByIdCommand {

  private final LeadTagRepository leadTagRepository;

  public GetLeadTagByIdCommandImpl(LeadTagRepository leadTagRepository) {
    this.leadTagRepository = leadTagRepository;
  }

  @Override
  public Mono<GetLeadTagWebResponse> execute(GetLeadTagByIdCommandRequest request) {
    return Mono.defer(() -> findTag(request))
        .map(this::toGetWebResponse);
  }

  private Mono<LeadTag> findTag(GetLeadTagByIdCommandRequest request) {
    return leadTagRepository.findByDeletedFalseAndCompanyGroupIdAndId(request.getCompanyGroupId(), request.getId())
        .switchIfEmpty(Mono.error(new MicroserviceValidationException(ErrorCode.TAG_NOT_EXIST)));
  }

  private GetLeadTagWebResponse toGetWebResponse(LeadTag leadTag) {
    return GetLeadTagWebResponse.builder()
        .id(leadTag.getId())
        .companyGroupId(leadTag.getCompanyGroupId())
        .name(leadTag.getName())
        .description(leadTag.getDescription())
        .build();
  }
}

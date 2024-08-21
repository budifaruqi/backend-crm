package com.example.test.command.impl.leadTag;

import com.example.test.command.leadTag.UpdateLeadTagByIdCommand;
import com.example.test.command.model.leadTag.UpdateLeadTagByIdCommandRequest;
import com.example.test.common.constant.ErrorCode;
import com.example.test.common.helper.response.exception.MicroserviceValidationException;
import com.example.test.repository.LeadTagRepository;
import com.example.test.repository.model.LeadTag;
import com.example.test.web.model.response.leadTag.GetLeadTagWebResponse;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UpdateLeadTagByIdCommandImpl implements UpdateLeadTagByIdCommand {

  private final LeadTagRepository leadTagRepository;

  public UpdateLeadTagByIdCommandImpl(LeadTagRepository leadTagRepository) {
    this.leadTagRepository = leadTagRepository;
  }

  @Override
  public Mono<GetLeadTagWebResponse> execute(UpdateLeadTagByIdCommandRequest request) {
    return Mono.defer(() -> findLeadTag(request))
        .flatMap(leadTag -> Mono.defer(() -> checkName(leadTag, request))
            .map(s -> toLeadTag(leadTag, request))
            .flatMap(leadTagRepository::save)
            .map(this::toGetWebResponse));
  }

  private Mono<LeadTag> findLeadTag(UpdateLeadTagByIdCommandRequest request) {
    return leadTagRepository.findByDeletedFalseAndCompanyGroupIdAndId(request.getCompanyGroupId(), request.getId())
        .switchIfEmpty(Mono.error(new MicroserviceValidationException(ErrorCode.TAG_NOT_EXIST)));
  }

  private Mono<LeadTag> checkName(LeadTag leadTag, UpdateLeadTagByIdCommandRequest request) {
    return leadTagRepository.findByDeletedFalseAndCompanyGroupIdAndName(request.getCompanyGroupId(), request.getName())
        .switchIfEmpty(Mono.fromSupplier(() -> LeadTag.builder()
            .build()))
        .filter(s -> s.getId() == null || s.getId()
            .equals(leadTag.getId()))
        .switchIfEmpty(Mono.error(new MicroserviceValidationException(ErrorCode.NAME_ALREADY_USED)));
  }

  private LeadTag toLeadTag(LeadTag leadTag, UpdateLeadTagByIdCommandRequest request) {
    leadTag.setName(request.getName());
    leadTag.setDescription(request.getDescription());

    return leadTag;
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

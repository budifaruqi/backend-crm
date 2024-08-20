package com.example.test.command.impl.leadTag;

import com.example.test.command.leadTag.CreateLeadTagCommand;
import com.example.test.command.model.leadTag.CreateLeadTagCommandRequest;
import com.example.test.common.constant.ErrorCode;
import com.example.test.common.helper.response.exception.MicroserviceValidationException;
import com.example.test.repository.LeadTagRepository;
import com.example.test.repository.model.LeadTag;
import com.example.test.web.configuration.CustomAuditorAware;
import com.example.test.web.model.response.leadTag.GetLeadTagWebResponse;
import com.solusinegeri.validation.model.exception.ValidationException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
public class CreateLeadTagCommandImpl implements CreateLeadTagCommand {

  private final LeadTagRepository leadTagRepository;

  private final CustomAuditorAware customAuditorAware;

  public CreateLeadTagCommandImpl(LeadTagRepository leadTagRepository, CustomAuditorAware customAuditorAware) {
    this.leadTagRepository = leadTagRepository;
    this.customAuditorAware = customAuditorAware;
  }

  @Override
  public Mono<GetLeadTagWebResponse> execute(CreateLeadTagCommandRequest request) {
    return Mono.defer(() -> checkName(request))
        .map(s -> toLeadTag(request))
        .flatMap(leadTagRepository::save)
        .map(this::toGetWebResponse);
  }

  private Mono<LeadTag> checkName(CreateLeadTagCommandRequest request) {
    return leadTagRepository.findByDeletedFalseAndCompanyGroupIdAndName(request.getCompanyGroupId(), request.getName())
        .switchIfEmpty(Mono.fromSupplier(() -> LeadTag.builder()
            .build()))
        .filter(s -> !Objects.equals(s.getName(), request.getName()))
        .switchIfEmpty(Mono.error(new MicroserviceValidationException(ErrorCode.NAME_ALREADY_USED)));
  }

  private LeadTag toLeadTag(CreateLeadTagCommandRequest request) {
    return LeadTag.builder()
        .companyGroupId(request.getCompanyGroupId())
        .name(request.getName())
        .description(request.getDescription())
        .build();
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

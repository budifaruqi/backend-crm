package com.example.test.command.impl.partner;

import com.example.test.command.model.partner.CreatePartnerCommandRequest;
import com.example.test.command.partner.CreatePartnerCommand;
import com.example.test.common.constant.ErrorCode;
import com.example.test.common.helper.response.exception.MicroserviceValidationException;
import com.example.test.repository.PartnerRepository;
import com.example.test.repository.model.Partner;
import com.example.test.web.model.response.partner.GetPartnerWebResponse;
import com.solusinegeri.validation.model.exception.ValidationException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
public class CreatePartnerCommandImpl implements CreatePartnerCommand {

  private final PartnerRepository partnerRepository;

  public CreatePartnerCommandImpl(PartnerRepository partnerRepository) {
    this.partnerRepository = partnerRepository;
  }

  @Override
  public Mono<GetPartnerWebResponse> execute(CreatePartnerCommandRequest request) {
    return Mono.defer(() -> checkName(request))
        .map(s -> toLeadTag(request))
        .flatMap(partnerRepository::save)
        .map(this::toGetWebResponse);
  }

  private Mono<Partner> checkName(CreatePartnerCommandRequest request) {
    return partnerRepository.findByDeletedFalseAndCompanyIdAndName(request.getCompanyId(), request.getName())
        .switchIfEmpty(Mono.fromSupplier(() -> Partner.builder()
            .build()))
        .filter(s -> !Objects.equals(s.getName(), request.getName()))
        .switchIfEmpty(Mono.error(new MicroserviceValidationException(ErrorCode.NAME_ALREADY_USED)));
  }

  private Partner toLeadTag(CreatePartnerCommandRequest request) {
    return Partner.builder()
        .companyId(request.getCompanyId())
        .name(request.getName())
        .description(request.getDescription())
        .build();
  }

  private GetPartnerWebResponse toGetWebResponse(Partner leadTag) {
    return GetPartnerWebResponse.builder()
        .id(leadTag.getId())
        .companyId(leadTag.getCompanyId())
        .name(leadTag.getName())
        .description(leadTag.getDescription())
        .build();
  }
}

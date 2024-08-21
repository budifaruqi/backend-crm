package com.example.test.command.impl.partner;

import com.example.test.command.model.partner.GetPartnerByIdCommandRequest;
import com.example.test.command.partner.GetPartnerByIdCommand;
import com.example.test.common.constant.ErrorCode;
import com.example.test.common.helper.response.exception.MicroserviceValidationException;
import com.example.test.repository.PartnerRepository;
import com.example.test.repository.model.Partner;
import com.example.test.web.model.response.partner.GetPartnerWebResponse;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class GetPartnerByIdCommandImpl implements GetPartnerByIdCommand {

  private final PartnerRepository partnerRepository;

  public GetPartnerByIdCommandImpl(PartnerRepository partnerRepository) {
    this.partnerRepository = partnerRepository;
  }

  @Override
  public Mono<GetPartnerWebResponse> execute(GetPartnerByIdCommandRequest request) {
    return Mono.defer(() -> findTag(request))
        .map(this::toGetWebResponse);
  }

  private Mono<Partner> findTag(GetPartnerByIdCommandRequest request) {
    return partnerRepository.findByDeletedFalseAndCompanyIdAndId(request.getCompanyId(), request.getId())
        .switchIfEmpty(Mono.error(new MicroserviceValidationException(ErrorCode.PARTNER_NOT_EXIST)));
  }

  private GetPartnerWebResponse toGetWebResponse(Partner partner) {
    return GetPartnerWebResponse.builder()
        .id(partner.getId())
        .companyId(partner.getCompanyId())
        .name(partner.getName())
        .description(partner.getDescription())
        .build();
  }
}

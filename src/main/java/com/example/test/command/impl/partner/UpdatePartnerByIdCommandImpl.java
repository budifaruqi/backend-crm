package com.example.test.command.impl.partner;

import com.example.test.command.model.partner.UpdatePartnerByIdCommandRequest;
import com.example.test.command.partner.UpdatePartnerByIdCommand;
import com.example.test.common.constant.ErrorCode;
import com.example.test.common.helper.response.exception.MicroserviceValidationException;
import com.example.test.repository.PartnerRepository;
import com.example.test.repository.model.Partner;
import com.example.test.web.model.response.partner.GetPartnerWebResponse;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
public class UpdatePartnerByIdCommandImpl implements UpdatePartnerByIdCommand {

  private final PartnerRepository partnerRepository;

  public UpdatePartnerByIdCommandImpl(PartnerRepository partnerRepository) {
    this.partnerRepository = partnerRepository;
  }

  @Override
  public Mono<GetPartnerWebResponse> execute(UpdatePartnerByIdCommandRequest request) {
    return Mono.defer(() -> findPartner(request))
        .flatMap(partner -> Mono.defer(() -> checkRequest(request))
            .map(s -> updatePartner(partner, request))
            .flatMap(partnerRepository::save))
        .map(this::toGetWebResponse);
  }

  private Mono<Partner> findPartner(UpdatePartnerByIdCommandRequest request) {
    return partnerRepository.findByDeletedFalseAndCompanyIdAndId(request.getCompanyId(), request.getId())
        .switchIfEmpty(Mono.error(new MicroserviceValidationException(ErrorCode.PARENT_NOT_EXIST)));
  }

  private Mono<Partner> checkRequest(UpdatePartnerByIdCommandRequest request) {
    return partnerRepository.findByDeletedFalseAndCompanyIdAndName(request.getCompanyId(), request.getName())
        .switchIfEmpty(Mono.fromSupplier(() -> Partner.builder()
            .build()))
        .filter(s -> s.getId() == null || Objects.equals(s.getId(), request.getId()))
        .switchIfEmpty(Mono.error(new MicroserviceValidationException(ErrorCode.NAME_ALREADY_USED)));
  }

  private Partner updatePartner(Partner partner, UpdatePartnerByIdCommandRequest request) {
    partner.setName(request.getName());
    partner.setDescription(partner.getDescription());

    return partner;
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

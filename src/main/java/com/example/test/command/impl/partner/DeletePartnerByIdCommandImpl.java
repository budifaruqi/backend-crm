package com.example.test.command.impl.partner;

import com.example.test.command.model.partner.DeletePartnerByIdCommandRequest;
import com.example.test.command.model.partner.UpdatePartnerByIdCommandRequest;
import com.example.test.command.partner.DeletePartnerByIdCommand;
import com.example.test.common.constant.ErrorCode;
import com.example.test.common.helper.response.exception.MicroserviceValidationException;
import com.example.test.repository.PartnerRepository;
import com.example.test.repository.model.Partner;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
public class DeletePartnerByIdCommandImpl implements DeletePartnerByIdCommand {

  private final PartnerRepository partnerRepository;

  public DeletePartnerByIdCommandImpl(PartnerRepository partnerRepository) {
    this.partnerRepository = partnerRepository;
  }

  @Override
  public Mono<Void> execute(DeletePartnerByIdCommandRequest request) {
    return Mono.defer(() -> findPartner(request))
        .map(partner -> deletePartner(partner, request))
        .flatMap(partnerRepository::save)
        .then();
  }

  private Mono<Partner> findPartner(DeletePartnerByIdCommandRequest request) {
    return partnerRepository.findByDeletedFalseAndCompanyIdAndId(request.getCompanyId(), request.getId())
        .switchIfEmpty(Mono.error(new MicroserviceValidationException(ErrorCode.PARTNER_NOT_EXIST)));
  }

  private Mono<Partner> checkRequest(UpdatePartnerByIdCommandRequest request) {
    return partnerRepository.findByDeletedFalseAndCompanyIdAndName(request.getCompanyId(), request.getName())
        .switchIfEmpty(Mono.fromSupplier(() -> Partner.builder()
            .build()))
        .filter(s -> s.getId() == null || Objects.equals(s.getId(), request.getId()))
        .switchIfEmpty(Mono.error(new MicroserviceValidationException(ErrorCode.NAME_ALREADY_USED)));
  }

  private Partner deletePartner(Partner partner, DeletePartnerByIdCommandRequest request) {
    partner.setDeleted(true);

    return partner;
  }
}

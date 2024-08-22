package com.example.test.command.impl.lead;

import com.example.test.command.lead.CreateLeadCommand;
import com.example.test.command.model.lead.CreateLeadCommandRequest;
import com.example.test.common.constant.ErrorCode;
import com.example.test.common.enums.LeadStatus;
import com.example.test.common.helper.response.exception.MicroserviceValidationException;
import com.example.test.repository.BankRepository;
import com.example.test.repository.LeadRepository;
import com.example.test.repository.LeadTagRepository;
import com.example.test.repository.model.Bank;
import com.example.test.repository.model.Lead;
import com.example.test.repository.model.LeadTag;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;

@Service
public class CreateLeadCommandImpl implements CreateLeadCommand {

  private final LeadRepository leadRepository;

  private final LeadTagRepository leadTagRepository;

  private final BankRepository bankRepository;

  public CreateLeadCommandImpl(LeadRepository leadRepository, LeadTagRepository leadTagRepository,
      BankRepository bankRepository) {
    this.leadRepository = leadRepository;
    this.leadTagRepository = leadTagRepository;
    this.bankRepository = bankRepository;
  }

  @Override
  public Mono<Object> execute(CreateLeadCommandRequest request) {
    return Mono.defer(() -> checkName(request))
        .flatMap(s -> checkBank(request))
        .flatMap(s -> checkTag(request))
        .map(tags -> toLead(tags, request))
        .flatMap(leadRepository::save);
  }

  private Mono<Lead> checkName(CreateLeadCommandRequest request) {
    return leadRepository.findByDeletedFalseAndCompanyGroupIdAndName(request.getCompanyGroupId(), request.getName())
        .switchIfEmpty(Mono.fromSupplier(() -> Lead.builder()
            .build()))
        .filter(s -> !Objects.equals(s.getName(), request.getName()))
        .switchIfEmpty(Mono.error(new MicroserviceValidationException(ErrorCode.NAME_ALREADY_USED)));
  }

  private Mono<List<String>> checkTag(CreateLeadCommandRequest request) {
    return Flux.fromIterable(request.getTags())
        .flatMapSequential(tagId -> findTag(request, tagId))
        .map(LeadTag::getId)
        .collectList();
  }

  private Mono<LeadTag> findTag(CreateLeadCommandRequest request, String tagId) {
    return leadTagRepository.findByDeletedFalseAndCompanyGroupIdAndId(request.getCompanyGroupId(), tagId)
        .switchIfEmpty(Mono.error(new MicroserviceValidationException(ErrorCode.TAG_NOT_EXIST)));
  }

  private Mono<Bank> checkBank(CreateLeadCommandRequest request) {
    return bankRepository.findByDeletedFalseAndCompanyGroupIdAndId(request.getCompanyGroupId(), request.getBankId())
        .switchIfEmpty(Mono.error(new MicroserviceValidationException(ErrorCode.BANK_NOT_EXIST)));
  }

  //NOTE : Belum Check Sales ID
  private Lead toLead(List<String> tags, CreateLeadCommandRequest request) {
    return Lead.builder()
        .companyGroupId(request.getCompanyGroupId())
        .name(request.getName())
        .picName(request.getPicName())
        .picPhone(request.getPicPhone())
        .picEmail(request.getPicEmail())
        .description(request.getDescription())
        .address(request.getAddress())
        .city(request.getCity())
        .province(request.getProvince())
        .gMapLink(request.getGMapLink())
        .potentialSize(request.getPotentialSize())
        .potentialRevenue(request.getPotentialRevenue())
        .facebook(request.getFacebook())
        .instagram(request.getInstagram())
        .tags(tags)
        .salesId(request.getSalesId())
        .bankId(request.getBankId())
        .status(LeadStatus.NEW)
        .reference(request.getReference())
        .build();
  }
}

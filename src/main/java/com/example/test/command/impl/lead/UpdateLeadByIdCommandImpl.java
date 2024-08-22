package com.example.test.command.impl.lead;

import com.example.test.command.lead.UpdateLeadByIdCommand;
import com.example.test.command.model.lead.UpdateLeadByIdCommandRequest;
import com.example.test.common.constant.ErrorCode;
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

@Service
public class UpdateLeadByIdCommandImpl implements UpdateLeadByIdCommand {

  private final LeadRepository leadRepository;

  private final BankRepository bankRepository;

  private final LeadTagRepository leadTagRepository;

  public UpdateLeadByIdCommandImpl(LeadRepository leadRepository, BankRepository bankRepository,
      LeadTagRepository leadTagRepository) {
    this.leadRepository = leadRepository;
    this.bankRepository = bankRepository;
    this.leadTagRepository = leadTagRepository;
  }

  @Override
  public Mono<Object> execute(UpdateLeadByIdCommandRequest request) {
    return Mono.defer(() -> findLead(request))
        .flatMap(lead -> Mono.defer(() -> checkRequest(request))
            .map(tags -> toLead(lead, request, tags))
            .flatMap(leadRepository::save));
  }

  private Mono<Lead> findLead(UpdateLeadByIdCommandRequest request) {
    return leadRepository.findByDeletedFalseAndCompanyGroupIdAndId(request.getCompanyGroupId(), request.getId())
        .switchIfEmpty(Mono.error(new MicroserviceValidationException(ErrorCode.LEAD_NOT_EXIST)));
  }

  private Mono<List<String>> checkRequest(UpdateLeadByIdCommandRequest request) {
    return Mono.defer(() -> checkBank(request))
        .flatMap(bank -> checkTag(request));
  }

  private Mono<Bank> checkBank(UpdateLeadByIdCommandRequest request) {
    return bankRepository.findByDeletedFalseAndCompanyGroupIdAndId(request.getCompanyGroupId(), request.getBankId())
        .switchIfEmpty(Mono.error(new MicroserviceValidationException(ErrorCode.BANK_NOT_EXIST)));
  }
  //NOTE : Belum Check Sales ID

  private Mono<List<String>> checkTag(UpdateLeadByIdCommandRequest request) {
    return Flux.fromIterable(request.getTags())
        .flatMapSequential(tagId -> findTag(request, tagId))
        .map(LeadTag::getId)
        .collectList();
  }

  private Mono<LeadTag> findTag(UpdateLeadByIdCommandRequest request, String tagId) {
    return leadTagRepository.findByDeletedFalseAndCompanyGroupIdAndId(request.getCompanyGroupId(), tagId)
        .switchIfEmpty(Mono.error(new MicroserviceValidationException(ErrorCode.TAG_NOT_EXIST)));
  }

  private Lead toLead(Lead lead, UpdateLeadByIdCommandRequest request, List<String> tags) {
    lead.setPicName(request.getPicName());
    lead.setPicPhone(request.getPicPhone());
    lead.setPicEmail(request.getPicEmail());
    lead.setDescription(request.getDescription());
    lead.setAddress(request.getAddress());
    lead.setCity(request.getCity());
    lead.setProvince(request.getProvince());
    lead.setGMapLink(request.getGMapLink());
    lead.setPotentialSize(request.getPotentialSize());
    lead.setPotentialRevenue(request.getPotentialRevenue());
    lead.setFacebook(request.getFacebook());
    lead.setInstagram(request.getInstagram());
    lead.setTags(request.getTags());
    lead.setSalesId(request.getSalesId());
    lead.setBankId(request.getBankId());
    lead.setReference(request.getReference());

    return lead;
  }
}

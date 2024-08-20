package com.example.test.command.impl.lead;

import com.example.test.command.lead.CreateLeadCommand;
import com.example.test.command.model.lead.CreateLeadCommandRequest;
import com.example.test.common.constant.ErrorCode;
import com.example.test.common.enums.LeadStatus;
import com.example.test.common.enums.PotentialLeadStatus;
import com.example.test.common.helper.response.exception.MicroserviceValidationException;
import com.example.test.repository.LeadRepository;
import com.example.test.repository.LeadTagRepository;
import com.example.test.repository.PartnerRepository;
import com.example.test.repository.model.Lead;
import com.example.test.repository.model.LeadTag;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class CreateLeadCommandImpl implements CreateLeadCommand {

  private final PartnerRepository partnerRepository;

  private final LeadTagRepository leadTagRepository;


  private final LeadRepository leadRepository;

  public CreateLeadCommandImpl(PartnerRepository partnerRepository, LeadTagRepository leadTagRepository,
      LeadRepository leadRepository) {
    this.partnerRepository = partnerRepository;
    this.leadTagRepository = leadTagRepository;
    this.leadRepository = leadRepository;
  }

  @Override
  public Mono<Object> execute(CreateLeadCommandRequest request) {
    return Mono.defer(() -> checkPotentialLead(request))
        .flatMap(potentialLead -> Mono.defer(() -> checkExistLead(potentialLead, request))
            .flatMap(s -> checkTag(request))
            .map(tags -> toLead(potentialLead, tags, request))
            .flatMap(leadRepository::save));
  }

  private Mono<PotentialLead> checkPotentialLead(CreateLeadCommandRequest request) {
    return potentialLeadRepository.findByDeletedFalseAndCompanyIdAndId(request.getCompanyId(),
            request.getPotentialLeadId())
        .switchIfEmpty(Mono.error(new MicroserviceValidationException(ErrorCode.POTENTIAL_LEAD_NOT_EXIST)))
        .filter(potentialLead -> potentialLead.getStatus() == PotentialLeadStatus.APPROVED)
        .switchIfEmpty(Mono.error(new MicroserviceValidationException(ErrorCode.POTENTIAL_LEAD_STATUS_NOT_VALID)));
  }

  private Mono<Boolean> checkExistLead(PotentialLead potentialLead, CreateLeadCommandRequest request) {
    return leadRepository.existsByDeletedFalseAndCompanyIdAndPotentialLeadId(request.getCompanyId(),
            potentialLead.getId())
        .filter(s -> !s)
        .switchIfEmpty(Mono.error(new MicroserviceValidationException(ErrorCode.POTENTIAL_LEAD_ALREADY_USED)));
  }

  private Mono<List<String>> checkTag(CreateLeadCommandRequest request) {
    return Flux.fromIterable(request.getLeadTagIds())
        .flatMapSequential(tagId -> findTag(request, tagId))
        .map(LeadTag::getId)
        .collectList();
  }

  private Mono<LeadTag> findTag(CreateLeadCommandRequest request, String tagId) {
    return leadTagRepository.findByDeletedFalseAndCompanyGroupIdAndId(request.getCompanyId(), tagId)
        .switchIfEmpty(Mono.error(new MicroserviceValidationException(ErrorCode.TAG_NOT_EXIST)));
  }

  private Lead toLead(PotentialLead potentialLead, List<String> tags, CreateLeadCommandRequest request) {
    return Lead.builder()
        .companyId(request.getCompanyId())
        .potentialLeadId(potentialLead.getId())
        .name(potentialLead.getName())
        .picName(request.getPicName())
        .picPhone(request.getPicPhone())
        .picEmail(request.getPicEmail())
        .description(request.getDescription())
        .tags(tags)
        .partnerId(potentialLead.getPartnerId())
        .address(request.getAddress())
        .city(request.getCity())
        .province(request.getProvince())
        .gMapLink(request.getGMapLink())
        .potentialSize(request.getPotentialSize())
        .potentialRevenue(request.getPotentialRevenue())
        .facebook(request.getFacebook())
        .instagram(request.getInstagram())
        .reference(request.getReference())
        .status(LeadStatus.NEW)
        .isCustomer(false)
        .isLive(false)
        .isDormant(false)
        .build();
  }
}

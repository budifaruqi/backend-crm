package com.example.test.command.impl.lead;

import com.example.test.command.lead.GetLeadByIdCommand;
import com.example.test.command.model.lead.GetLeadByIdCommandRequest;
import com.example.test.common.constant.ErrorCode;
import com.example.test.common.helper.response.exception.MicroserviceValidationException;
import com.example.test.common.vo.TagVO;
import com.example.test.repository.LeadRepository;
import com.example.test.repository.LeadTagRepository;
import com.example.test.repository.PartnerRepository;
import com.example.test.repository.model.Lead;
import com.example.test.repository.model.LeadTag;
import com.example.test.repository.model.Partner;
import com.example.test.web.model.response.lead.GetLeadWebResponse;
import com.solusinegeri.validation.model.exception.ValidationException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class GetLeadByIdCommandImpl implements GetLeadByIdCommand {

  private final LeadRepository leadRepository;

  private final LeadTagRepository leadTagRepository;

  private final PartnerRepository partnerRepository;

  public GetLeadByIdCommandImpl(LeadRepository leadRepository, LeadTagRepository leadTagRepository,
      PartnerRepository partnerRepository) {
    this.leadRepository = leadRepository;
    this.leadTagRepository = leadTagRepository;
    this.partnerRepository = partnerRepository;
  }

  @Override
  public Mono<GetLeadWebResponse> execute(GetLeadByIdCommandRequest request) {
    return Mono.defer(() -> getLead(request))
        .flatMap(lead -> Mono.defer(() -> getPartner(lead))
            .flatMap(partner -> Mono.defer(() -> checkTag(lead))
                .map(tagVOS -> toGetWebResponse(lead, partner, tagVOS))));
  }

  private Mono<Lead> getLead(GetLeadByIdCommandRequest request) {
    return leadRepository.findByDeletedFalseAndCompanyIdAndId(request.getCompanyId(), request.getId())
        .switchIfEmpty(Mono.error(new MicroserviceValidationException(ErrorCode.LEAD_NOT_EXIST)));
  }

  private Mono<Partner> getPartner(Lead potentialLead) {
    return partnerRepository.findByDeletedFalseAndCompanyIdAndId(potentialLead.getCompanyId(),
            potentialLead.getPartnerId())
        .switchIfEmpty(Mono.fromSupplier(() -> Partner.builder()
            .name(ErrorCode.PARTNER_NOT_EXIST)
            .build()));
  }

  private Mono<List<TagVO>> checkTag(Lead lead) {
    return Flux.fromIterable(lead.getTags())
        .flatMapSequential(tagId -> findTag(lead, tagId))
        .map(this::toVO)
        .collectList();
  }

  private Mono<LeadTag> findTag(Lead request, String tagId) {
    return leadTagRepository.findByDeletedFalseAndCompanyGroupIdAndId(request.getCompanyId(), tagId)
        .switchIfEmpty(Mono.fromSupplier(() -> LeadTag.builder()
            .name(ErrorCode.TAG_NOT_EXIST)
            .build()));
  }

  private TagVO toVO(LeadTag leadTag) {
    return TagVO.builder()
        .id(leadTag.getId())
        .name(leadTag.getName())
        .build();
  }

  private GetLeadWebResponse toGetWebResponse(Lead lead, Partner partner, List<TagVO> tagVOS) {
    return GetLeadWebResponse.builder()
        .id(lead.getId())
        .companyId(lead.getCompanyId())
        .potentialLeadId(lead.getPotentialLeadId())
        .name(lead.getName())
        .picName(lead.getPicName())
        .picPhone(lead.getPicPhone())
        .picEmail(lead.getPicEmail())
        .description(lead.getDescription())
        .tags(tagVOS)
        .partnerId(lead.getPartnerId())
        .partnerName(partner.getName())
        .address(lead.getAddress())
        .city(lead.getCity())
        .province(lead.getProvince())
        .gMapLink(lead.getGMapLink())
        .potentialSize(lead.getPotentialSize())
        .potentialRevenue(lead.getPotentialRevenue())
        .facebook(lead.getFacebook())
        .instagram(lead.getInstagram())
        .reference(lead.getReference())
        .status(lead.getStatus())
        .isCustomer(lead.getIsCustomer())
        .isLive(lead.getIsLive())
        .isDormant(lead.getIsDormant())
        .build();
  }
}

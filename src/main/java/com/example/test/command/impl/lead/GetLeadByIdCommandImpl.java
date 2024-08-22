package com.example.test.command.impl.lead;

import com.example.test.command.lead.GetLeadByIdCommand;
import com.example.test.command.model.lead.GetLeadByIdCommandRequest;
import com.example.test.common.constant.ErrorCode;
import com.example.test.common.helper.response.exception.MicroserviceValidationException;
import com.example.test.common.vo.TagVO;
import com.example.test.repository.BankRepository;
import com.example.test.repository.LeadRepository;
import com.example.test.repository.LeadTagRepository;
import com.example.test.repository.model.Bank;
import com.example.test.repository.model.Lead;
import com.example.test.repository.model.LeadTag;
import com.example.test.web.model.response.lead.GetLeadWebResponse;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class GetLeadByIdCommandImpl implements GetLeadByIdCommand {

  private final LeadRepository leadRepository;

  private final LeadTagRepository leadTagRepository;

  private final BankRepository bankRepository;

  public GetLeadByIdCommandImpl(LeadRepository leadRepository, LeadTagRepository leadTagRepository,
      BankRepository bankRepository) {
    this.leadRepository = leadRepository;
    this.leadTagRepository = leadTagRepository;
    this.bankRepository = bankRepository;
  }

  @Override
  public Mono<GetLeadWebResponse> execute(GetLeadByIdCommandRequest request) {
    return Mono.defer(() -> getLead(request))
        .flatMap(lead -> Mono.defer(() -> getBank(lead))
            .flatMap(bank -> Mono.defer(() -> checkTag(lead))
                .map(tagVOS -> toGetWebResponse(lead, bank, tagVOS))));
  }

  private Mono<Lead> getLead(GetLeadByIdCommandRequest request) {
    return leadRepository.findByDeletedFalseAndCompanyGroupIdAndId(request.getCompanyGroupId(), request.getId())
        .switchIfEmpty(Mono.error(new MicroserviceValidationException(ErrorCode.LEAD_NOT_EXIST)));
  }

  private Mono<Bank> getBank(Lead lead) {
    return bankRepository.findByDeletedFalseAndCompanyGroupIdAndId(lead.getCompanyGroupId(), lead.getId())
        .switchIfEmpty(Mono.fromSupplier(() -> Bank.builder()
            .name(ErrorCode.BANK_NOT_EXIST)
            .build()));
  }

  private Mono<List<TagVO>> checkTag(Lead lead) {
    return Flux.fromIterable(lead.getTags())
        .flatMapSequential(tagId -> findTag(lead, tagId))
        .map(this::toVO)
        .collectList();
  }

  private Mono<LeadTag> findTag(Lead request, String tagId) {
    return leadTagRepository.findByDeletedFalseAndCompanyGroupIdAndId(request.getCompanyGroupId(), tagId)
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

  private GetLeadWebResponse toGetWebResponse(Lead lead, Bank bank, List<TagVO> tagVOS) {
    return GetLeadWebResponse.builder()
        .id(lead.getId())
        .companyGroupId(lead.getCompanyGroupId())
        .name(lead.getName())
        .picName(lead.getPicName())
        .picPhone(lead.getPicPhone())
        .picEmail(lead.getPicEmail())
        .description(lead.getDescription())
        .address(lead.getAddress())
        .city(lead.getCity())
        .province(lead.getProvince())
        .gMapLink(lead.getGMapLink())
        .potentialSize(lead.getPotentialSize())
        .potentialRevenue(lead.getPotentialRevenue())
        .facebook(lead.getFacebook())
        .instagram(lead.getInstagram())
        .tags(tagVOS)
        .salesId(lead.getSalesId())
        .salesName("BELOM")
        .bankId(lead.getBankId())
        .bankName(bank.getName())
        .status(lead.getStatus())
        .reference(lead.getReference())
        .build();
  }
}

package com.example.test.command.impl.bank;

import com.example.test.command.bank.GetAllBankCommand;
import com.example.test.command.model.bank.GetAllBankCommandRequest;
import com.example.test.repository.BankRepository;
import com.example.test.repository.model.Bank;
import com.example.test.web.model.response.bank.GetBankWebResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class GetAllBankCommandImpl implements GetAllBankCommand {

  private final BankRepository bankRepository;

  public GetAllBankCommandImpl(BankRepository bankRepository) {
    this.bankRepository = bankRepository;
  }

  @Override
  public Mono<Page<GetBankWebResponse>> execute(GetAllBankCommandRequest request) {
    return Mono.zip(getData(request), count(request))
        .map(objects -> toPageResponse(request, objects.getT1(), objects.getT2()));
  }

  private Mono<Long> count(GetAllBankCommandRequest request) {
    return bankRepository.countAllByDeletedFalseAndFilter(request.getCompanyGroupId(), request.getBankName(),
        request.getType(), request.getPageable());
  }

  private Mono<List<GetBankWebResponse>> getData(GetAllBankCommandRequest request) {
    return Flux.defer(() -> getBank(request))
        .map(this::toGetWebResponse)
        .collectList();
  }

  private Flux<Bank> getBank(GetAllBankCommandRequest request) {
    return bankRepository.findAllByDeletedFalseAndFilter(request.getCompanyGroupId(), request.getBankName(),
        request.getType(), request.getPageable());
  }

  private GetBankWebResponse toGetWebResponse(Bank bank) {
    return GetBankWebResponse.builder()
        .id(bank.getId())
        .companyGroupId(bank.getCompanyGroupId())
        .name(bank.getName())
        .type(bank.getType())
        .parentId(bank.getParentId())
        .build();
  }

  private PageImpl<GetBankWebResponse> toPageResponse(GetAllBankCommandRequest request,
      List<GetBankWebResponse> contents, Long total) {
    return new PageImpl<>(contents, request.getPageable(), total);
  }
}

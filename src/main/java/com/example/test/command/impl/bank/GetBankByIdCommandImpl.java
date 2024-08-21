package com.example.test.command.impl.bank;

import com.example.test.command.bank.GetBankByIdCommand;
import com.example.test.command.model.bank.GetBankByIdCommandRequest;
import com.example.test.common.constant.ErrorCode;
import com.example.test.common.helper.response.exception.MicroserviceValidationException;
import com.example.test.repository.BankRepository;
import com.example.test.repository.model.Bank;
import com.example.test.web.model.response.bank.GetBankWebResponse;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class GetBankByIdCommandImpl implements GetBankByIdCommand {

  private final BankRepository bankRepository;

  public GetBankByIdCommandImpl(BankRepository bankRepository) {
    this.bankRepository = bankRepository;
  }

  @Override
  public Mono<GetBankWebResponse> execute(GetBankByIdCommandRequest request) {
    return Mono.defer(() -> findTag(request))
        .map(this::toGetWebResponse);
  }

  private Mono<Bank> findTag(GetBankByIdCommandRequest request) {
    return bankRepository.findByDeletedFalseAndCompanyGroupIdAndId(request.getCompanyGroupId(), request.getId())
        .switchIfEmpty(Mono.error(new MicroserviceValidationException(ErrorCode.BANK_NOT_EXIST)));
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
}

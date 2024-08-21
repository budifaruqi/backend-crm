package com.example.test.command.impl.bank;

import com.example.test.command.bank.UpdateBankByIdCommand;
import com.example.test.command.model.bank.UpdateBankByIdCommandRequest;
import com.example.test.common.constant.ErrorCode;
import com.example.test.common.helper.response.exception.MicroserviceValidationException;
import com.example.test.repository.BankRepository;
import com.example.test.repository.model.Bank;
import com.example.test.web.model.response.bank.GetBankWebResponse;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UpdateBankByIdCommandImpl implements UpdateBankByIdCommand {

  private final BankRepository bankRepository;

  public UpdateBankByIdCommandImpl(BankRepository bankRepository) {
    this.bankRepository = bankRepository;
  }

  @Override
  public Mono<GetBankWebResponse> execute(UpdateBankByIdCommandRequest request) {
    return Mono.defer(() -> findBank(request))
        .flatMap(bank -> Mono.defer(() -> checkName(bank, request))
            .map(s -> toBank(bank, request))
            .flatMap(bankRepository::save)
            .map(this::toGetWebResponse));
  }

  private Mono<Bank> findBank(UpdateBankByIdCommandRequest request) {
    return bankRepository.findByDeletedFalseAndCompanyGroupIdAndId(request.getCompanyGroupId(), request.getId())
        .switchIfEmpty(Mono.error(new MicroserviceValidationException(ErrorCode.BANK_NOT_EXIST)));
  }

  private Mono<Bank> checkName(Bank bank, UpdateBankByIdCommandRequest request) {
    return bankRepository.findByDeletedFalseAndCompanyGroupIdAndName(request.getCompanyGroupId(), request.getName())
        .switchIfEmpty(Mono.fromSupplier(() -> Bank.builder()
            .build()))
        .filter(s -> s.getId() == null || s.getId()
            .equals(bank.getId()))
        .switchIfEmpty(Mono.error(new MicroserviceValidationException(ErrorCode.NAME_ALREADY_USED)));
  }

  private Bank toBank(Bank bank, UpdateBankByIdCommandRequest request) {
    bank.setName(request.getName());

    return bank;
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

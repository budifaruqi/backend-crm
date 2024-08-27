package com.example.test.command.impl.bank;

import com.example.test.command.bank.CreateBankCommand;
import com.example.test.command.model.bank.CreateBankCommandRequest;
import com.example.test.common.constant.ErrorCode;
import com.example.test.common.enums.BankType;
import com.example.test.common.helper.response.exception.MicroserviceValidationException;
import com.example.test.repository.BankRepository;
import com.example.test.repository.model.Bank;
import com.example.test.web.model.response.bank.GetBankWebResponse;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
public class CreateBankCommandImpl implements CreateBankCommand {

  private final BankRepository bankRepository;

  public CreateBankCommandImpl(BankRepository bankRepository) {
    this.bankRepository = bankRepository;
  }

  @Override
  public Mono<GetBankWebResponse> execute(CreateBankCommandRequest request) {
    return Mono.defer(() -> checkName(request))
        .flatMap(s -> checkParent(request))
        .map(s -> toBank(request))
        .flatMap(bankRepository::save)
        .map(this::toGetWebResponse);
  }

  private Mono<Bank> checkName(CreateBankCommandRequest request) {
    return bankRepository.findByDeletedFalseAndCompanyGroupIdAndName(request.getCompanyGroupId(), request.getName())
        .switchIfEmpty(Mono.fromSupplier(() -> Bank.builder()
            .build()))
        .filter(s -> !Objects.equals(s.getName(), request.getName()))
        .switchIfEmpty(Mono.error(new MicroserviceValidationException(ErrorCode.NAME_ALREADY_USED)));
  }

  private Mono<Bank> checkParent(CreateBankCommandRequest request) {
    if (request.getType() != BankType.PUSAT) {
      return bankRepository.findByDeletedFalseAndCompanyGroupIdAndId(request.getCompanyGroupId(), request.getParentId())
          .switchIfEmpty(Mono.error(new MicroserviceValidationException(ErrorCode.PARENT_NOT_EXIST)));
    }
    return null;
  }

  private Bank toBank(CreateBankCommandRequest request) {
    return Bank.builder()
        .companyGroupId(request.getCompanyGroupId())
        .name(request.getName())
        .type(request.getType())
        .parentId(request.getParentId())
        .build();
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

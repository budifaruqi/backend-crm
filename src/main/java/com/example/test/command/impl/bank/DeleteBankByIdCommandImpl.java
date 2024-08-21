package com.example.test.command.impl.bank;

import com.example.test.command.bank.DeleteBankByIdCommand;
import com.example.test.command.model.bank.DeleteBankByIdCommandRequest;
import com.example.test.common.constant.ErrorCode;
import com.example.test.common.helper.response.exception.MicroserviceValidationException;
import com.example.test.repository.BankRepository;
import com.example.test.repository.model.Bank;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
public class DeleteBankByIdCommandImpl implements DeleteBankByIdCommand {

  private final BankRepository bankRepository;

  public DeleteBankByIdCommandImpl(BankRepository bankRepository) {
    this.bankRepository = bankRepository;
  }

  @Override
  public Mono<Void> execute(DeleteBankByIdCommandRequest request) {
    return Mono.defer(() -> findBank(request))
        .flatMap(bank -> Mono.defer(() -> checkChildBank(bank))
            .filter(bankChild -> Objects.equals(bank.getId(), bankChild.getId()))
            .switchIfEmpty(Mono.error(new MicroserviceValidationException(ErrorCode.BANK_CHILD_EXIST)))
            .map(this::deleteBank)
            .flatMap(bankRepository::save))
        .then();
  }

  private Mono<Bank> findBank(DeleteBankByIdCommandRequest request) {
    return bankRepository.findByDeletedFalseAndCompanyGroupIdAndId(request.getCompanyGroupId(), request.getId())
        .switchIfEmpty(Mono.error(new MicroserviceValidationException(ErrorCode.BANK_NOT_EXIST)));
  }

  private Mono<Bank> checkChildBank(Bank bank) {
    return bankRepository.findChildBank(bank)
        .switchIfEmpty(Mono.fromSupplier(() -> bank));
  }

  private Bank deleteBank(Bank bank) {
    bank.setDeleted(true);

    return bank;
  }
}

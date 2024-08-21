package com.example.test.repository;

import com.example.test.common.enums.BankType;
import com.example.test.repository.model.Bank;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BankRepositoryFilter {

  Mono<Bank> findChildBank(Bank bank);

  Mono<Long> countAllByDeletedFalseAndFilter(String companyGroupId, String bankName, BankType type, Pageable pageable);

  Flux<Bank> findAllByDeletedFalseAndFilter(String companyGroupId, String bankName, BankType type, Pageable pageable);
}

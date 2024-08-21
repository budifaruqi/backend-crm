package com.example.test.repository;

import com.example.test.repository.model.Bank;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface BankRepository extends ReactiveMongoRepository<Bank, String>, BankRepositoryFilter {

  Mono<Bank> findByDeletedFalseAndCompanyGroupIdAndName(String companyGroupId, String name);

  Mono<Bank> findByDeletedFalseAndCompanyGroupIdAndId(String companyGroupId, String id);
}

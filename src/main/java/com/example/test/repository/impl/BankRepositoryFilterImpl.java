package com.example.test.repository.impl;

import com.example.test.common.constant.CollectionName;
import com.example.test.common.enums.BankType;
import com.example.test.repository.BankRepositoryFilter;
import com.example.test.repository.model.Bank;
import com.solusinegeri.mongodb.helper.QueryBuilder;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

public class BankRepositoryFilterImpl implements BankRepositoryFilter {

  private final ReactiveMongoTemplate reactiveMongoTemplate;

  public BankRepositoryFilterImpl(ReactiveMongoTemplate reactiveMongoTemplate) {
    this.reactiveMongoTemplate = reactiveMongoTemplate;
  }

  @Override
  public Mono<Bank> findChildBank(Bank bank) {
    Query query = getChildQuery(bank);
    return reactiveMongoTemplate.findOne(query, Bank.class, CollectionName.BANK);
  }

  @Override
  public Mono<Long> countAllByDeletedFalseAndFilter(String companyGroupId, String bankName, BankType type,
      Pageable pageable) {
    Query query = getQuery(companyGroupId, bankName, type, null);
    return reactiveMongoTemplate.count(query, Bank.class, CollectionName.BANK);
  }

  @Override
  public Flux<Bank> findAllByDeletedFalseAndFilter(String companyGroupId, String bankName, BankType type,
      Pageable pageable) {
    Query query = getQuery(companyGroupId, bankName, type, pageable);
    return reactiveMongoTemplate.find(query, Bank.class, CollectionName.BANK);
  }

  private Query getChildQuery(Bank bank) {
    return QueryBuilder.create()
        .andEqual("companyGroupId", bank.getCompanyGroupId())
        .andEqual("deleted", false)
        .andEqual("parentId", bank.getId())
        .build();
  }

  private Query getQuery(String companyGroupId, String bankName, BankType type, Pageable pageable) {
    Query query = QueryBuilder.create()
        .andEqual("deleted", false)
        .andEqual("companyGroupId", companyGroupId)
        .andEqual("type", type)
        .andLikeIgnoreCase("name", bankName)
        .build();

    if (Objects.nonNull(pageable)) {
      query.with(pageable);
    }
    return query;
  }
}

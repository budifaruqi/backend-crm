package com.example.test.repository.impl;

import com.example.test.common.constant.CollectionName;
import com.example.test.repository.PartnerRepositoryFilter;
import com.example.test.repository.model.Partner;
import com.solusinegeri.mongodb.helper.QueryBuilder;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Repository

public class PartnerRepositoryFilterImpl implements PartnerRepositoryFilter {

  private final ReactiveMongoTemplate reactiveMongoTemplate;

  public PartnerRepositoryFilterImpl(ReactiveMongoTemplate reactiveMongoTemplate) {
    this.reactiveMongoTemplate = reactiveMongoTemplate;
  }

  @Override
  public Flux<Partner> findAllByDeletedFalseAndFilter(String companyId, String name, Pageable pageable) {
    Query query = getQuery(companyId, name, pageable);
    return reactiveMongoTemplate.find(query, Partner.class, CollectionName.PARTNER);
  }

  @Override
  public Mono<Long> countAllByDeletedFalseAndFilter(String companyId, String name, Pageable pageable) {
    Query query = getQuery(companyId, name, null);
    return reactiveMongoTemplate.count(query, Partner.class, CollectionName.PARTNER);
  }

  private Query getQuery(String companyId, String name, Pageable pageable) {
    Query query = QueryBuilder.create()
        .andEqual("deleted", false)
        .andEqual("companyId", companyId)
        .andLikeIgnoreCase("name", name)
        .build();

    if (Objects.nonNull(pageable)) {
      query.with(pageable);
    }
    return query;
  }
}

package com.example.test.repository.impl;

import com.example.test.common.constant.CollectionName;
import com.example.test.repository.LeadTagRepositoryFilter;
import com.example.test.repository.model.LeadTag;
import com.solusinegeri.mongodb.helper.QueryBuilder;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Repository
public class LeadTagRepositoryFilterImpl implements LeadTagRepositoryFilter {

  private final ReactiveMongoTemplate reactiveMongoTemplate;

  public LeadTagRepositoryFilterImpl(ReactiveMongoTemplate reactiveMongoTemplate) {
    this.reactiveMongoTemplate = reactiveMongoTemplate;
  }

  @Override
  public Mono<Long> countAllByDeletedFalseAndFilter(String companyId, String tagName, Pageable pageable) {
    Query query = getQuery(companyId, tagName, null);
    return reactiveMongoTemplate.count(query, LeadTag.class, CollectionName.LEAD_TAG);
  }

  @Override
  public Flux<LeadTag> findAllByDeletedFalseAndFilter(String companyId, String tagName, Pageable pageable) {
    Query query = getQuery(companyId, tagName, pageable);
    return reactiveMongoTemplate.find(query, LeadTag.class, CollectionName.LEAD_TAG);
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

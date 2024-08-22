package com.example.test.repository.impl;

import com.example.test.common.constant.CollectionName;
import com.example.test.common.enums.PotentialLeadStatus;
import com.example.test.repository.PotentialLeadRepositoryFilter;
import com.example.test.repository.model.PotentialLead;
import com.solusinegeri.mongodb.helper.QueryBuilder;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Repository
public class PotentialLeadRepositoryFilterImpl implements PotentialLeadRepositoryFilter {

  private final ReactiveMongoTemplate reactiveMongoTemplate;

  public PotentialLeadRepositoryFilterImpl(ReactiveMongoTemplate reactiveMongoTemplate) {
    this.reactiveMongoTemplate = reactiveMongoTemplate;
  }

  @Override
  public Mono<Long> countAllByDeletedFalseAndFilter(String companyGroupId, String name, PotentialLeadStatus status,
      Pageable pageable) {
    Query query = getQuery(companyGroupId, name, status, null);
    return reactiveMongoTemplate.count(query, PotentialLead.class, CollectionName.POTENTIAL_LEAD);
  }

  @Override
  public Flux<PotentialLead> findAllByDeletedFalseAndFilter(String companyGroupId, String name,
      PotentialLeadStatus status, Pageable pageable) {
    Query query = getQuery(companyGroupId, name, status, pageable);
    return reactiveMongoTemplate.find(query, PotentialLead.class, CollectionName.POTENTIAL_LEAD);
  }

  private Query getQuery(String companyGroupId, String name, PotentialLeadStatus status, Pageable pageable) {
    Query query = QueryBuilder.create()
        .andEqual("deleted", false)
        .andEqual("companyGroupId", companyGroupId)
        .andEqual("status", status)
        .andLikeIgnoreCase("name", name)
        .build();

    if (Objects.nonNull(pageable)) {
      query.with(pageable);
    }
    return query;
  }
}

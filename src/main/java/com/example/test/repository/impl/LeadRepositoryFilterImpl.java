package com.example.test.repository.impl;

import com.example.test.common.constant.CollectionName;
import com.example.test.common.enums.LeadStatus;
import com.example.test.repository.LeadRepositoryFilter;
import com.example.test.repository.model.Lead;
import com.solusinegeri.mongodb.helper.QueryBuilder;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;

@Repository
public class LeadRepositoryFilterImpl implements LeadRepositoryFilter {

  private final ReactiveMongoTemplate reactiveMongoTemplate;

  public LeadRepositoryFilterImpl(ReactiveMongoTemplate reactiveMongoTemplate) {
    this.reactiveMongoTemplate = reactiveMongoTemplate;
  }

  @Override
  public Mono<Long> countAllByDeletedFalseAndFilter(String companyId, String name, List<String> tagIds, String city,
      String province, String reference, LeadStatus status, Pageable pageable) {
    Query query = getQuery(companyId, name, tagIds, city, province, reference, status, null);
    return reactiveMongoTemplate.count(query, Lead.class, CollectionName.LEAD);
  }

  @Override
  public Flux<Lead> findAllByDeletedFalseAndFilter(String companyId, String name, List<String> tagIds, String city,
      String province, String reference, LeadStatus status, Pageable pageable) {
    Query query = getQuery(companyId, name, tagIds, city, province, reference, status, pageable);
    return reactiveMongoTemplate.find(query, Lead.class, CollectionName.LEAD);
  }

  private Query getQuery(String companyId, String name, List<String> tagIds, String city, String province,
      String reference, LeadStatus status, Pageable pageable) {
    QueryBuilder queryBuilder = QueryBuilder.create()
        .andEqual("deleted", false)
        .andEqual("companyId", companyId)
        .andEqual("status", status)
        .andLikeIgnoreCase("city", city)
        .andLikeIgnoreCase("province", province)
        .andLikeIgnoreCase("reference", reference)
        .andLikeIgnoreCase("name", name);

    Query query = queryBuilder.build();
    if (Objects.nonNull(pageable)) {
      query.with(pageable);
    }
    return query;
  }
}

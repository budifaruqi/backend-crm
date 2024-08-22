package com.example.test.repository.impl;

import com.example.test.common.constant.CollectionName;
import com.example.test.common.enums.FollowUpActivity;
import com.example.test.common.enums.FollowUpStatus;
import com.example.test.repository.FollowUpRepositoryFilter;
import com.example.test.repository.model.FollowUp;
import com.solusinegeri.mongodb.helper.QueryBuilder;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Repository
public class FollowUpRepositoryFilterImpl implements FollowUpRepositoryFilter {

  private final ReactiveMongoTemplate reactiveMongoTemplate;

  public FollowUpRepositoryFilterImpl(ReactiveMongoTemplate reactiveMongoTemplate) {
    this.reactiveMongoTemplate = reactiveMongoTemplate;
  }

  @Override
  public Mono<Long> countAllByDeletedFalseAndFilter(String companyGroupId, String salesId, String leadId,
      FollowUpActivity activity, FollowUpStatus status, Pageable pageable) {
    Query query = getQuery(companyGroupId, salesId, leadId, activity, status, null);
    return reactiveMongoTemplate.count(query, FollowUp.class, CollectionName.FOLLOW_UP);
  }

  @Override
  public Flux<FollowUp> findAllByDeletedFalseAndFilter(String companyGroupId, String salesId, String leadId,
      FollowUpActivity activity, FollowUpStatus status, Pageable pageable) {
    Query query = getQuery(companyGroupId, salesId, leadId, activity, status, pageable);
    return reactiveMongoTemplate.find(query, FollowUp.class, CollectionName.FOLLOW_UP);
  }

  private Query getQuery(String companyGroupId, String salesId, String leadId, FollowUpActivity activity,
      FollowUpStatus status, Pageable pageable) {
    Query query = QueryBuilder.create()
        .andEqual("deleted", false)
        .andEqual("companyGroupId", companyGroupId)
        .andEqual("salesId", salesId)
        .andEqual("leadId", leadId)
        .andEqual("activity", activity)
        .andEqual("status", status)
        .build();

    if (Objects.nonNull(pageable)) {
      query.with(pageable);
    }
    return query;
  }
}

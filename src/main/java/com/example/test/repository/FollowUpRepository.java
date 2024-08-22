package com.example.test.repository;

import com.example.test.repository.model.FollowUp;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface FollowUpRepository extends ReactiveMongoRepository<FollowUp, String>, FollowUpRepositoryFilter {

  Mono<FollowUp> findByDeletedFalseAndCompanyGroupIdAndSalesIdAndId(String companyGroupId, String salesId, String id);
}

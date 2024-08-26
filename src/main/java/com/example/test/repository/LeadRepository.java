package com.example.test.repository;

import com.example.test.repository.model.Lead;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface LeadRepository extends ReactiveMongoRepository<Lead, String>, LeadRepositoryFilter {

  Mono<Lead> findByDeletedFalseAndCompanyGroupIdAndId(String companyGroupId, String id);

  Mono<Lead> findByDeletedFalseAndCompanyGroupIdAndName(String companyGroupId, String name);

  Mono<Boolean> existsByDeletedFalseAndCompanyGroupIdAndId(String companyGroupId, String externalId);
}

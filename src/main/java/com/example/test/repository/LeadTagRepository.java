package com.example.test.repository;

import com.example.test.repository.model.LeadTag;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface LeadTagRepository extends ReactiveMongoRepository<LeadTag, String>, LeadTagRepositoryFilter {

  Mono<LeadTag> findByDeletedFalseAndCompanyGroupIdAndName(String companyGroupId, String name);

  Mono<LeadTag> findByDeletedFalseAndCompanyGroupIdAndId(String companyGroupId, String id);
}

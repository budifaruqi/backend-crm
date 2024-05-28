package com.example.test.repository;

import com.example.test.repository.model.LeadTag;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface LeadTagRepository extends ReactiveMongoRepository<LeadTag, String>, LeadTagRepositoryFilter {

  Mono<LeadTag> findByDeletedFalseAndCompanyIdAndName(String companyId, String name);

  Mono<LeadTag> findByDeletedFalseAndCompanyIdAndId(String companyId, String id);
}

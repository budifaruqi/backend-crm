package com.example.test.repository;

import com.example.test.repository.model.Lead;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface LeadRepository extends ReactiveMongoRepository<Lead, String>, LeadRepositoryFilter {

  Mono<Lead> findByDeletedFalseAndCompanyIdAndPotentialLeadId(String companyId, String id);

  Mono<Boolean> existsByDeletedFalseAndCompanyIdAndPotentialLeadId(String companyId, String id);

  Mono<Lead> findByDeletedFalseAndCompanyIdAndId(String companyId, String id);
}

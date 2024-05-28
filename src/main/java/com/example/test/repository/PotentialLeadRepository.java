package com.example.test.repository;

import com.example.test.repository.model.PotentialLead;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface PotentialLeadRepository
    extends ReactiveMongoRepository<PotentialLead, String>, PotentialLeadRepositoryFilter {

  Mono<PotentialLead> findByDeletedFalseAndCompanyIdAndName(String companyId, String name);

  Mono<PotentialLead> findByDeletedFalseAndCompanyIdAndId(String companyId, String id);

  Mono<PotentialLead> findByDeletedFalseAndCompanyIdAndPartnerId(String companyId, String partnerId);
}

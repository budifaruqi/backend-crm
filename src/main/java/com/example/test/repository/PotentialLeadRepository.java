package com.example.test.repository;

import com.example.test.repository.model.PotentialLead;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface PotentialLeadRepository
    extends ReactiveMongoRepository<PotentialLead, String>, PotentialLeadRepositoryFilter {

  Mono<PotentialLead> findByDeletedFalseAndCompanyGroupIdAndName(String companyId, String name);

  Mono<PotentialLead> findByDeletedFalseAndCompanyGroupIdAndId(String companyId, String id);
}

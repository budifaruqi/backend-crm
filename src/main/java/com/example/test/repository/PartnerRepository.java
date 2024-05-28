package com.example.test.repository;

import com.example.test.repository.model.Partner;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface PartnerRepository extends ReactiveMongoRepository<Partner, String>, PartnerRepositoryFilter {

  Mono<Partner> findByDeletedFalseAndCompanyIdAndName(String companyId, String name);

  Mono<Partner> findByDeletedFalseAndCompanyIdAndId(String companyId, String id);
}

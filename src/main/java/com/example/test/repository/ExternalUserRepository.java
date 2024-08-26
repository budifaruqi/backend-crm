package com.example.test.repository;

import com.example.test.repository.model.ExternalUser;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface ExternalUserRepository
    extends ReactiveMongoRepository<ExternalUser, String>, ExternalUserRepositoryFilter {

  Mono<ExternalUser> findByDeletedFalseAndCompanyGroupIdAndUsername(String companyGroupId, String username);
}

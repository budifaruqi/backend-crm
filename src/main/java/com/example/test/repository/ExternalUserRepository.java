package com.example.test.repository;

import com.example.test.repository.model.ExternalUser;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ExternalUserRepository
    extends ReactiveMongoRepository<ExternalUser, String>, ExternalUserRepositoryFilter {}

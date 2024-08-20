package com.example.test.repository;

import com.example.test.repository.model.LeadTag;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface LeadTagRepositoryFilter {

  Mono<Long> countAllByDeletedFalseAndFilter(String companyGroupId, String tagName, Pageable pageable);

  Flux<LeadTag> findAllByDeletedFalseAndFilter(String companyGroupId, String tagName, Pageable pageable);
}

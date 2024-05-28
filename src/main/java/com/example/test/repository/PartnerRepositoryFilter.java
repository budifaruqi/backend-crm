package com.example.test.repository;

import com.example.test.repository.model.Partner;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PartnerRepositoryFilter {

  Flux<Partner> findAllByDeletedFalseAndFilter(String companyId, String name, Pageable pageable);

  Mono<Long> countAllByDeletedFalseAndFilter(String companyId, String name, Pageable pageable);
}

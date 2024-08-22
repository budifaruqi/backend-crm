package com.example.test.repository;

import com.example.test.common.enums.PotentialLeadStatus;
import com.example.test.repository.model.PotentialLead;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PotentialLeadRepositoryFilter {

  Flux<PotentialLead> findAllByDeletedFalseAndFilter(String companyGroupId, String name, PotentialLeadStatus status,
      Pageable pageable);

  Mono<Long> countAllByDeletedFalseAndFilter(String companyGroupId, String name, PotentialLeadStatus status,
      Pageable pageable);
}

package com.example.test.repository;

import com.example.test.common.enums.LeadStatus;
import com.example.test.repository.model.Lead;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface LeadRepositoryFilter {

  Mono<Long> countAllByDeletedFalseAndFilter(String companyGroupId, String name, List<String> tagIds, String city,
      String province, String reference, LeadStatus status, Pageable pageable);

  Flux<Lead> findAllByDeletedFalseAndFilter(String companyGroupId, String name, List<String> tagIds, String city,
      String province, String reference, LeadStatus status, Pageable pageable);
}

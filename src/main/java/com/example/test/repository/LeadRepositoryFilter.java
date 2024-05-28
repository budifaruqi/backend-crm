package com.example.test.repository;

import com.example.test.common.enums.LeadStatus;
import com.example.test.repository.model.Lead;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface LeadRepositoryFilter {

  Mono<Long> countAllByDeletedFalseAndFilter(String companyId, String name, List<String> tagIds, String partnerId,
      String city, String province, String reference, LeadStatus status, Boolean isCustomer, Boolean isLive,
      Boolean isDormant, Pageable pageable);

  Flux<Lead> findAllByDeletedFalseAndFilter(String companyId, String name, List<String> tagIds, String partnerId,
      String city, String province, String reference, LeadStatus status, Boolean isCustomer, Boolean isLive,
      Boolean isDormant, Pageable pageable);
}

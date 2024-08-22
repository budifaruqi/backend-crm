package com.example.test.repository;

import com.example.test.common.enums.FollowUpActivity;
import com.example.test.common.enums.FollowUpStatus;
import com.example.test.repository.model.FollowUp;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FollowUpRepositoryFilter {

  Mono<Long> countAllByDeletedFalseAndFilter(String companyGroupId, String salesId, String leadId,
      FollowUpActivity activity, FollowUpStatus status, Pageable pageable);

  Flux<FollowUp> findAllByDeletedFalseAndFilter(String companyGroupId, String salesId, String leadId,
      FollowUpActivity activity, FollowUpStatus status, Pageable pageable);
}

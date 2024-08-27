package com.example.test.web.configuration;

import com.example.test.web.model.resolver.AccessTokenParameter;
import org.springframework.data.domain.ReactiveAuditorAware;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class CustomAuditorAware implements ReactiveAuditorAware<String> {

  private final AuditingDataHolder auditingDataHolder;

  public CustomAuditorAware(AuditingDataHolder auditingDataHolder) {
    this.auditingDataHolder = auditingDataHolder;
  }

  @Override
  public Mono<String> getCurrentAuditor() {
    AccessTokenParameter auditingData = auditingDataHolder.getAuditingData();
    if (auditingData != null) {
      return Mono.justOrEmpty(auditingData.getAccountId());
    }
    return Mono.empty();
  }
}

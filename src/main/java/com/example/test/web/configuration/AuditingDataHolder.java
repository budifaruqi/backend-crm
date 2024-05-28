package com.example.test.web.configuration;

import com.example.test.web.model.resolver.AccessTokenParameter;
import org.springframework.stereotype.Component;

@Component
public class AuditingDataHolder {

  private AccessTokenParameter auditingData;

  public AccessTokenParameter getAuditingData() {
    return auditingData;
  }

  public void setAuditingData(AccessTokenParameter auditingData) {
    this.auditingData = auditingData;
  }
}

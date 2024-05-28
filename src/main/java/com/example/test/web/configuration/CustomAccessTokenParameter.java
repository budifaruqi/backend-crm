package com.example.test.web.configuration;

import com.example.test.web.model.resolver.AccessTokenParameter;

public class CustomAccessTokenParameter extends AccessTokenParameter {

  // Add additional fields for custom auditor information
  private String customAuditorField;

  public String getCustomAuditorField() {
    return customAuditorField;
  }

  public void setCustomAuditorField(String customAuditorField) {
    this.customAuditorField = customAuditorField;
  }
}

package com.example.test.validation.validator;

import com.example.test.common.enums.LeadStatus;
import com.example.test.validation.annotation.LeadStatusSubset;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class LeadStatusSubsetValidator implements ConstraintValidator<LeadStatusSubset, LeadStatus> {

  @Override
  public boolean isValid(LeadStatus status, ConstraintValidatorContext context) {
    return status != LeadStatus.NEW;
  }
}

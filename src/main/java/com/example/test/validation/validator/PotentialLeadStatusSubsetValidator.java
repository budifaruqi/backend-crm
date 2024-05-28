package com.example.test.validation.validator;

import com.example.test.common.enums.PotentialLeadStatus;
import com.example.test.validation.annotation.PotentialLeadStatusSubset;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

public class PotentialLeadStatusSubsetValidator
    implements ConstraintValidator<PotentialLeadStatusSubset, PotentialLeadStatus> {

  private PotentialLeadStatus[] subset;

  @Override
  public void initialize(PotentialLeadStatusSubset constraintAnnotation) {
    this.subset = constraintAnnotation.anyOf();
  }

  @Override
  public boolean isValid(PotentialLeadStatus status, ConstraintValidatorContext constraintValidatorContext) {
    return status == null || Arrays.asList(subset)
        .contains(status);
  }
}

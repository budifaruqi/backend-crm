package com.example.test.validation.validator;

import com.example.test.validation.annotation.StringLength;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class StringLengthValidator implements ConstraintValidator<StringLength, String> {

  private Long min;

  private Long max;

  @Override
  public void initialize(StringLength constraintAnnotation) {
    this.max = constraintAnnotation.max();
    this.min = constraintAnnotation.min();
  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    return value.length() >= min && value.length() <= max;
  }
}

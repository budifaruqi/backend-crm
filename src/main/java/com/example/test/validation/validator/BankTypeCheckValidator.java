package com.example.test.validation.validator;

import com.example.test.command.model.bank.CreateBankCommandRequest;
import com.example.test.common.enums.BankType;
import com.example.test.validation.annotation.BankTypeCheck;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class BankTypeCheckValidator implements ConstraintValidator<BankTypeCheck, CreateBankCommandRequest> {

  @Override
  public boolean isValid(CreateBankCommandRequest request, ConstraintValidatorContext context) {
    // If the type is not PUSAT, check if parentId is not blank
    if (request.getType() != BankType.PUSAT) {
      return request.getParentId() != null && !request.getParentId()
          .isEmpty();
    }
    // If type is PUSAT, no need to check parentId
    request.setParentId(null);
    return request.getParentId() == null;
  }
}

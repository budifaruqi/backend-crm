package com.example.test.common.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ErrorCode {

  public static final String AUTH_FAILED = "AUTH_FAILED";

  public static final String BANK_CHILD_EXIST = "BANK_CHILD_EXIST";

  public static final String BANK_NOT_EXIST = "BANK_NOT_EXIST";

  public static final String FAILED_CREATE_ACCOUNT = "FAILED_CREATE_ACCOUNT";

  public static final String FOLLOW_UP_NOT_EXIST = "FOLLOW_UP_NOT_EXIST";

  public static final String LEAD_NOT_EXIST = "LEAD_NOT_EXIST";

  public static final String NAME_ALREADY_USED = "NAME_ALREADY_USED";

  public static final String PARENT_ID_MUST_NOT_BLANK = "PARENT_ID_MUST_NOT_BLANK";

  public static final String PARTNER_ALREADY_USED = "PARTNER_ALREADY_USED";

  public static final String PARENT_NOT_EXIST = "PARTNER_NOT_EXIST";

  public static final String POTENTIAL_LEAD_ALREADY_USED = "POTENTIAL_LEAD_ALREADY_USED";

  public static final String POTENTIAL_LEAD_NOT_EXIST = "POTENTIAL_LEAD_NOT_EXIST";

  public static final String POTENTIAL_LEAD_STATUS_NOT_VALID = "POTENTIAL_LEAD_STATUS_NOT_VALID";

  public static final String PRODUCT_NOT_EXIST = "PRODUCT_NOT_EXIST";

  public static final String CATEGORY_NOT_EXIST = "CATEGORY_NOT_EXIST";

  public static final String STATUS_NOT_VALID = "STATUS_NOT_VALID";

  public static final String TAG_NOT_EXIST = "TAG_NOT_EXIST";

  public static final String USERNAME_ALREADY_USED = "USERNAME_ALREADY_USED";

  public static final String VALUE_MUST_BE_GREATER_THAN_0 = "VALUE_MUST_BE_GREATER_THAN_0";

  public static final String WRONG_EXTERNAL_ID = "WRONG_EXTERNAL_ID";
}

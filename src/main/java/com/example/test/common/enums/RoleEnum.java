package com.example.test.common.enums;

public enum RoleEnum {
  admin("admins", "admin"),
  MEMBER("members", "member"),
  MERCHANT("merchants", "merchant"),
  sa("superadmins", "sa"),
  headoffice("headoffice", "headoffice"),
  ppic("ppic", "ppic"),
  warehouse("warehouse", "warehouse");

  public final String label;

  public final String auth;

  RoleEnum(String label, String auth) {
    this.label = label;
    this.auth = auth;
  }
}

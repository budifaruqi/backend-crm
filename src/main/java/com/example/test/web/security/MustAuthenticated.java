package com.example.test.web.security;

import com.example.test.common.enums.PermissionEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface MustAuthenticated {

  String operationId();

  PermissionEnum[] userPermission() default {};

  boolean checkPermission() default false;

  boolean value() default true;
}

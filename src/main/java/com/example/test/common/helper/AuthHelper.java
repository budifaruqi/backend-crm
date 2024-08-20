package com.example.test.common.helper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthHelper {

  public static String hashNfc(String nfcId, String secret) {
    SecretKeySpec secretKeySpec = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
    Mac mac = null;
    try {
      mac = Mac.getInstance("HmacSHA256");
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException(e);
    }
    try {
      mac.init(secretKeySpec);
    } catch (InvalidKeyException e) {
      throw new RuntimeException(e);
    }

    return Base64.getEncoder()
        .encodeToString(mac.doFinal(nfcId.getBytes()));
  }
}

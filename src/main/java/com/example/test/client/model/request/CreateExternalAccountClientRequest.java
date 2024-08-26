package com.example.test.client.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateExternalAccountClientRequest {

  private String name;

  private String username;

  private String email;

  private String phone;

  private String password;

  private String companyCategoryId;

  private List<String> features;

  private String createdBy;
}

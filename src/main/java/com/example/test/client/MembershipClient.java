package com.example.test.client;

import com.example.test.client.model.request.CreateExternalAccountClientRequest;
import com.example.test.client.model.request.ValidatePrivilegeClientRequest;
import com.example.test.client.model.response.AuthenticationClientResponse;
import com.example.test.client.model.response.CreateExternalAccountClientResponse;
import com.example.test.client.model.response.DetailClientResponse;
import reactor.core.publisher.Mono;

public interface MembershipClient {

  Mono<DetailClientResponse<AuthenticationClientResponse>> validatePrivilege(String token,
      ValidatePrivilegeClientRequest request);

  Mono<DetailClientResponse<CreateExternalAccountClientResponse>> createExternalUser(
      CreateExternalAccountClientRequest externalUserClientRequest);
}

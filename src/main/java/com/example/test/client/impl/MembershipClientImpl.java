package com.example.test.client.impl;

import com.example.test.client.MembershipClient;
import com.example.test.client.model.request.CreateExternalAccountClientRequest;
import com.example.test.client.model.request.ValidatePrivilegeClientRequest;
import com.example.test.client.model.response.AuthenticationClientResponse;
import com.example.test.client.model.response.DetailClientResponse;
import com.example.test.common.constant.ErrorCode;
import com.example.test.common.helper.response.exception.MicroserviceValidationException;
import com.solusinegeri.common.model.exception.ForbiddenException;
import com.solusinegeri.common.model.exception.UnauthorizedException;
import com.solusinegeri.common.service.JsonService;
import com.solusinegeri.web_client.properties.WebClientProperties;
import com.solusinegeri.web_client.reactive.BaseWebClient;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.function.Function;

@Component
@Slf4j
public class MembershipClientImpl extends BaseWebClient implements MembershipClient {

  private static final String configName = "membership";

  private final JsonService jsonService;

  public MembershipClientImpl(WebClientProperties clientProperties, JsonService jsonService) {
    super(clientProperties, configName);
    this.jsonService = jsonService;
  }

  @Override
  public Mono<DetailClientResponse<AuthenticationClientResponse>> validatePrivilege(String companyId, String token,
      ValidatePrivilegeClientRequest request) {

    return client.post()
        .uri(uriBuilder -> uriBuilder.path("/auth/private/privilege")
            .build())
        .headers(httpHeaders -> {
          httpHeaders.set("X-Asis-CompanyId", companyId);
          httpHeaders.set("Authorization", token);
        })
        .body(BodyInserters.fromValue(request))
        .accept(MediaType.APPLICATION_JSON)
        .exchangeToMono(clientResponse -> clientResponse.bodyToMono(
            new ParameterizedTypeReference<DetailClientResponse<AuthenticationClientResponse>>() {}))
        .map(s -> {
          System.out.println(s);
          return s;
        });
  }

  @Override
  public Mono<DetailClientResponse<AuthenticationClientResponse>> createExternalUser(
      CreateExternalAccountClientRequest request) {
    return client.post()
        .uri(uriBuilder -> uriBuilder.path("/user/private/account_external/create_or_update")
            .build())
        .contentType(MediaType.APPLICATION_JSON)
        .body(BodyInserters.fromValue(request))
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        .onStatus(HttpStatusCode::isError, toErrorValidationClientResponse(ErrorCode.FAILED_CREATE_ACCOUNT))
        .bodyToMono(new ParameterizedTypeReference<DetailClientResponse<AuthenticationClientResponse>>() {});
  }

  @Override
  protected Logger getLogger() {
    return log;
  }

  private Function<ClientResponse, Mono<? extends Throwable>> toErrorAuthenticationClientResponse() {
    return clientResponse -> clientResponse.bodyToMono(Object.class)
        .filter(Objects::nonNull)
        .doOnNext(r -> log.error(jsonService.map(r)))
        .map(errorResponse -> new UnauthorizedException(ErrorCode.AUTH_FAILED));
  }

  private Function<ClientResponse, Mono<? extends Throwable>> toErrorValidationClientResponse(String msg) {
    return clientResponse -> clientResponse.bodyToMono(Object.class)
        .filter(Objects::nonNull)
        .doOnNext(r -> log.error(jsonService.map(r)))
        .map(errorResponse -> new MicroserviceValidationException(msg));
  }

  private Function<ClientResponse, Mono<? extends Throwable>> toErrorForbiddenClientResponse() {
    return clientResponse -> clientResponse.bodyToMono(Object.class)
        .filter(Objects::nonNull)
        .doOnNext(r -> log.error(jsonService.map(r)))
        .map(errorResponse -> new ForbiddenException());
  }
}

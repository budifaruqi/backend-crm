package com.example.test.web.configuration;

import com.example.test.web.model.resolver.AccessTokenParameter;
import com.example.test.web.model.resolver.RequestTokenAccess;
import com.example.test.web.security.MustAuthenticated;
import com.example.test.web.security.MustAuthenticatedValidator;
import com.solusinegeri.security.helper.AnnotationHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.BindingContext;
import org.springframework.web.reactive.result.method.HandlerMethodArgumentResolver;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Component
@Slf4j
public class TokenAccessParameterArgumentResolver implements HandlerMethodArgumentResolver {

  private final MustAuthenticatedValidator mustAuthenticatedValidator;

  private final AuditingDataHolder auditingDataHolder;

  public TokenAccessParameterArgumentResolver(MustAuthenticatedValidator mustAuthenticatedValidator,
      AuditingDataHolder auditingDataHolder) {
    this.mustAuthenticatedValidator = mustAuthenticatedValidator;
    this.auditingDataHolder = auditingDataHolder;
  }

  @Override
  public boolean supportsParameter(MethodParameter methodParameter) {
    return AccessTokenParameter.class.isAssignableFrom(methodParameter.getParameterType());
  }

  @Override
  public Mono<Object> resolveArgument(MethodParameter methodParameter, BindingContext bindingContext,
      ServerWebExchange serverWebExchange) {
    return Mono.fromSupplier(serverWebExchange::getRequest)
        .map(this::toRequestTokenAccess)
        .flatMap(requestTokenAccess -> validate(methodParameter, requestTokenAccess))
        .flatMap(this::setAuditingHolder)
        .cast(Object.class);
  }

  private RequestTokenAccess toRequestTokenAccess(ServerHttpRequest serverHttpRequest) {
    return RequestTokenAccess.builder()
        .companyId(getCompanyIdHeader(serverHttpRequest))
        .token(getTokenHeader(serverHttpRequest))
        .build();
  }

  private String getTokenHeader(ServerHttpRequest serverHttpRequest) {
    return Optional.of(serverHttpRequest.getHeaders())
        .map(httpHeaders -> httpHeaders.getFirst("Authorization"))
        .orElse(StringUtils.EMPTY);
  }

  private String getCompanyIdHeader(ServerHttpRequest serverHttpRequest) {
    return Optional.of(serverHttpRequest.getHeaders())
        .map(httpHeaders -> httpHeaders.getFirst("X-ASIS-COMPANYID"))
        .orElse(StringUtils.EMPTY);
  }

  private Mono<AccessTokenParameter> validate(MethodParameter parameter, RequestTokenAccess requestTokenAccess) {
    return mustAuthenticatedValidator.throwIfInvalid(AnnotationHelper.getAnnotation(parameter, MustAuthenticated.class)
        .get(), requestTokenAccess);
  }

  private Mono<AccessTokenParameter> setAuditingHolder(AccessTokenParameter accessTokenParameter) {
    return Mono.fromCallable(() -> {
      auditingDataHolder.setAuditingData(accessTokenParameter);
      return accessTokenParameter;
    });
  }
}

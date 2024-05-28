package com.example.test.web.security;

import com.example.test.web.model.resolver.AccessTokenParameter;
import com.example.test.web.model.resolver.RequestTokenAccess;
import reactor.core.publisher.Mono;

public interface MustAuthenticatedValidator {

  Mono<AccessTokenParameter> throwIfInvalid(MustAuthenticated mustAuthenticated, RequestTokenAccess requestToken);
}

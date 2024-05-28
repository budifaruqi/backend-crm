package com.example.test.web.security;

import com.example.test.client.MembershipClient;
import com.example.test.client.model.request.ValidatePrivilegeClientRequest;
import com.example.test.client.model.response.AuthenticationClientResponse;
import com.example.test.common.constant.ErrorCode;
import com.example.test.common.enums.PermissionEnum;
import com.example.test.common.enums.RoleEnum;
import com.example.test.web.model.resolver.AccessTokenParameter;
import com.example.test.web.model.resolver.RequestTokenAccess;
import com.solusinegeri.common.model.exception.UnauthorizedException;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class MustAuthenticatedValidatorImpl implements MustAuthenticatedValidator {

  private final MembershipClient membershipClient;

  public MustAuthenticatedValidatorImpl(MembershipClient membershipClient) {
    this.membershipClient = membershipClient;
  }

  @Override
  public Mono<AccessTokenParameter> throwIfInvalid(MustAuthenticated mustAuthenticated,
      RequestTokenAccess requestTokenAccess) {
    return Mono.fromSupplier(() -> requestTokenAccess)
        .filter(check -> mustAuthenticated.value())
        .flatMap(token -> Mono.fromSupplier(() -> token)
            .flatMap(tokenReq -> checkTokenToAuth(tokenReq, mustAuthenticated)))
        .switchIfEmpty(createEmptyTokenAccessParameter());
  }

  private Mono<AccessTokenParameter> checkTokenToAuth(RequestTokenAccess tokenReq,
      MustAuthenticated mustAuthenticated) {
    System.out.println(createRequest(mustAuthenticated.userRole(), mustAuthenticated.userPermission()));
    return membershipClient.validatePrivilege(tokenReq.getCompanyId(), tokenReq.getToken(),
            createRequest(mustAuthenticated.userRole(), mustAuthenticated.userPermission()))
        .flatMap(s -> Mono.fromSupplier(() -> s)
            .filter(response -> Objects.equals(response.getStatus_code(), "200"))
            .map(response -> toAccessTokenParameter(response.getData()))
            .switchIfEmpty(Mono.error(new UnauthorizedException(ErrorCode.AUTH_FAILED))));
  }

  private AccessTokenParameter toAccessTokenParameter(AuthenticationClientResponse data) {
    return AccessTokenParameter.builder()
        .accountId(data.getAccountId())
        .companyGroupId(data.getCompanyGroupId())
        .companyId(data.getCompanyId())
        .companyGroupIdDefault(data.getCompanyGroupIdDefault())
        .companyIdDefault(data.getCompanyIdDefault())
        .roles(data.getRoles())
        .permissions(data.getPermissions())
        .name(data.getName())
        .username(data.getUsername())
        .acceptedRole(data.getAcceptedRole())
        .acceptedPermission(data.getAcceptedPermission())
        .build();
  }

  private Mono<AccessTokenParameter> createEmptyTokenAccessParameter() {
    return Mono.fromSupplier(() -> AccessTokenParameter.builder()
        .build());
  }

  private ValidatePrivilegeClientRequest createRequest(RoleEnum[] roleEnums, PermissionEnum[] permissionEnums) {
    List<String> formattedPermissions = Arrays.stream(permissionEnums)
        .map(permission -> "inventory:" + permission)
        .collect(Collectors.toList());

    return ValidatePrivilegeClientRequest.builder()
        .roles(Arrays.asList(roleEnums))
        .permissions(formattedPermissions)
        .build();
  }

  //  private boolean validate(MustAuthenticated mustAuthenticated, AccessTokenParameter param) {
  //    return Arrays.stream(mustAuthenticated.userRole())
  //        .anyMatch(userType -> userType.equals(param.getRole())) && StringUtils.isNotBlank(param.getUserId()) &&
  //        StringUtils.isNotBlank(param.getCompanyId());
  //  }
}

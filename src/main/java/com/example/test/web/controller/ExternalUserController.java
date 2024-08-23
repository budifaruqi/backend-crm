package com.example.test.web.controller;

import com.example.test.command.externalUser.CreateExternalUserCommand;
import com.example.test.command.model.externalUser.CreateExternalUserCommandRequest;
import com.example.test.common.constant.ResponseType;
import com.example.test.common.enums.RoleEnum;
import com.example.test.common.helper.response.MicroserviceResponse;
import com.example.test.common.helper.response.MicroserviceResponseHelper;
import com.example.test.web.model.request.externalUser.CreateExternalUserWebRequest;
import com.example.test.web.model.resolver.AccessTokenParameter;
import com.example.test.web.security.MustAuthenticated;
import com.solusinegeri.command.reactive.executor.CommandExecutor;
import com.solusinegeri.web.controller.reactive.BaseController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/crm/external-user")
@MustAuthenticated(userRole = {RoleEnum.sa})
public class ExternalUserController extends BaseController {

  public ExternalUserController(CommandExecutor executor) {
    super(executor);
  }

  @PostMapping
  public Mono<MicroserviceResponse<Object>> createExternalUser(AccessTokenParameter accessTokenParameter,
      @RequestBody CreateExternalUserWebRequest request) {
    CreateExternalUserCommandRequest commandRequest = CreateExternalUserCommandRequest.builder()
        .companyGroupId(accessTokenParameter.getCompanyGroupId())
        .salesId(accessTokenParameter.getAccountId())
        .externalId(request.getExternalId())
        .type(request.getType())
        .name(request.getName())
        .username(request.getUsername())
        .password(request.getPassword())
        .email(request.getEmail())
        .phone(request.getPhone())
        .build();

    return executor.execute(CreateExternalUserCommand.class, commandRequest)
        .map(data -> MicroserviceResponseHelper.ok(data, ResponseType.SUCCESS_CREATE_EXTERNAL_USER));
  }
}

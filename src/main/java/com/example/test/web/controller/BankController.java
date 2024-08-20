package com.example.test.web.controller;

import com.example.test.command.bank.CreateBankCommand;
import com.example.test.command.model.bank.CreateBankCommandRequest;
import com.example.test.common.constant.ResponseType;
import com.example.test.common.helper.response.MicroserviceResponse;
import com.example.test.common.helper.response.MicroserviceResponseHelper;
import com.example.test.web.model.request.bank.CreateBankWebRequest;
import com.example.test.web.model.resolver.AccessTokenParameter;
import com.example.test.web.model.response.bank.GetBankWebResponse;
import com.solusinegeri.command.reactive.executor.CommandExecutor;
import com.solusinegeri.web.controller.reactive.BaseController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/crm/bank-data")
//@MustAuthenticated(userRole = {RoleEnum.sa})
public class BankController extends BaseController {

  public BankController(CommandExecutor executor) {
    super(executor);
  }

  @PostMapping
  public Mono<MicroserviceResponse<GetBankWebResponse>> createBank(AccessTokenParameter accessTokenParameter,
      @RequestBody CreateBankWebRequest request) {
    CreateBankCommandRequest commandRequest = CreateBankCommandRequest.builder()
        .companyGroupId(accessTokenParameter.getCompanyGroupId())
        .name(request.getName())
        .type(request.getType())
        .parentId(request.getParentId())
        .build();

    return executor.execute(CreateBankCommand.class, commandRequest)
        .map(data -> MicroserviceResponseHelper.ok(data, ResponseType.SUCCESS_CREATE_BANK));
  }
}

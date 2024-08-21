package com.example.test.web.controller;

import com.example.test.command.bank.CreateBankCommand;
import com.example.test.command.bank.DeleteBankByIdCommand;
import com.example.test.command.bank.GetAllBankCommand;
import com.example.test.command.bank.GetBankByIdCommand;
import com.example.test.command.bank.UpdateBankByIdCommand;
import com.example.test.command.model.bank.CreateBankCommandRequest;
import com.example.test.command.model.bank.DeleteBankByIdCommandRequest;
import com.example.test.command.model.bank.GetAllBankCommandRequest;
import com.example.test.command.model.bank.GetBankByIdCommandRequest;
import com.example.test.command.model.bank.UpdateBankByIdCommandRequest;
import com.example.test.common.constant.ResponseType;
import com.example.test.common.enums.BankType;
import com.example.test.common.helper.response.MicroserviceResponse;
import com.example.test.common.helper.response.MicroserviceResponseHelper;
import com.example.test.web.model.request.bank.CreateBankWebRequest;
import com.example.test.web.model.request.bank.UpdateBankByIdWebRequest;
import com.example.test.web.model.response.bank.GetBankWebResponse;
import com.solusinegeri.command.helper.PagingHelper;
import com.solusinegeri.command.reactive.executor.CommandExecutor;
import com.solusinegeri.web.controller.reactive.BaseController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/crm/bank-data")
//@MustAuthenticated(userRole = {RoleEnum.sa})
public class BankController extends BaseController {

  public BankController(CommandExecutor executor) {
    super(executor);
  }

  @PostMapping
  public Mono<MicroserviceResponse<GetBankWebResponse>> createBank(@RequestBody CreateBankWebRequest request) {
    CreateBankCommandRequest commandRequest = CreateBankCommandRequest.builder()
        .companyGroupId("accessTokenParameter.getCompanyGroupId()")
        .name(request.getName())
        .type(request.getType())
        .parentId(request.getParentId())
        .build();

    return executor.execute(CreateBankCommand.class, commandRequest)
        .map(data -> MicroserviceResponseHelper.ok(data, ResponseType.SUCCESS_CREATE_BANK));
  }

  @GetMapping
  public Mono<MicroserviceResponse<List<GetBankWebResponse>>> getAllBank(
      @RequestParam(required = false) String bankName, @RequestParam(required = false) BankType type,
      @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size,
      @RequestParam(defaultValue = "-createdDate") String sortBy) {
    GetAllBankCommandRequest commandRequest = GetAllBankCommandRequest.builder()
        .companyGroupId("accessTokenParameter.getCompanyGroupId()")
        .bankName(bankName)
        .type(type)
        .pageable(PagingHelper.from(page, size, sortBy))
        .build();

    return executor.execute(GetAllBankCommand.class, commandRequest)
        .map(data -> MicroserviceResponseHelper.ok(data, ResponseType.SUCCESS_GET_BANK_DATA));
  }

  @GetMapping("/{id}")
  public Mono<MicroserviceResponse<GetBankWebResponse>> getBankById(@PathVariable String id) {
    GetBankByIdCommandRequest commandRequest = GetBankByIdCommandRequest.builder()
        .companyGroupId("accessTokenParameter.getCompanyGroupId()")
        .id(id)
        .build();

    return executor.execute(GetBankByIdCommand.class, commandRequest)
        .map(data -> MicroserviceResponseHelper.ok(data, ResponseType.SUCCESS_GET_BANK_DATA));
  }

  @PutMapping("/{id}")
  public Mono<MicroserviceResponse<GetBankWebResponse>> updateBankById(@PathVariable String id,
      @RequestBody UpdateBankByIdWebRequest request) {
    UpdateBankByIdCommandRequest commandRequest = UpdateBankByIdCommandRequest.builder()
        .companyGroupId("accessTokenParameter.getCompanyGroupId()")
        .id(id)
        .name(request.getName())
        .build();

    return executor.execute(UpdateBankByIdCommand.class, commandRequest)
        .map(data -> MicroserviceResponseHelper.ok(data, ResponseType.SUCCESS_UPDATE_BANK_DATA));
  }

  @DeleteMapping("/{id}")
  public Mono<MicroserviceResponse<Void>> deleteBankById(@PathVariable String id) {
    DeleteBankByIdCommandRequest commandRequest = DeleteBankByIdCommandRequest.builder()
        .companyGroupId("accessTokenParameter.getCompanyGroupId()")
        .id(id)
        .build();

    return executor.execute(DeleteBankByIdCommand.class, commandRequest)
        .map(data -> MicroserviceResponseHelper.ok(data, ResponseType.SUCCESS_DELETE_BANK));
  }
}

package com.example.test.web.controller;

import com.example.test.command.model.partner.CreatePartnerCommandRequest;
import com.example.test.command.model.partner.DeletePartnerByIdCommandRequest;
import com.example.test.command.model.partner.GetAllPartnerCommandRequest;
import com.example.test.command.model.partner.GetPartnerByIdCommandRequest;
import com.example.test.command.model.partner.UpdatePartnerByIdCommandRequest;
import com.example.test.command.partner.CreatePartnerCommand;
import com.example.test.command.partner.DeletePartnerByIdCommand;
import com.example.test.command.partner.GetAllPartnerCommand;
import com.example.test.command.partner.GetPartnerByIdCommand;
import com.example.test.command.partner.UpdatePartnerByIdCommand;
import com.example.test.common.enums.RoleEnum;
import com.example.test.web.model.request.partner.CreatePartnerWebRequest;
import com.example.test.web.model.request.partner.UpdatePartnerWebRequest;
import com.example.test.web.model.resolver.AccessTokenParameter;
import com.example.test.web.model.response.partner.GetPartnerWebResponse;
import com.example.test.web.security.MustAuthenticated;
import com.solusinegeri.command.helper.PagingHelper;
import com.solusinegeri.command.reactive.executor.CommandExecutor;
import com.solusinegeri.common.helper.ResponseHelper;
import com.solusinegeri.common.model.web.response.Response;
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
@RequestMapping("/crm/partner")
@MustAuthenticated(userRole = {RoleEnum.sa})

public class PartnerController extends BaseController {

  public PartnerController(CommandExecutor executor) {
    super(executor);
  }

  @PostMapping
  public Mono<Response<GetPartnerWebResponse>> createPartner(AccessTokenParameter accessTokenParameter,
      @RequestBody CreatePartnerWebRequest request) {
    CreatePartnerCommandRequest commandRequest = CreatePartnerCommandRequest.builder()
        .companyId(accessTokenParameter.getCompanyId())
        .name(request.getName())
        .description(request.getDescription())
        .build();

    return executor.execute(CreatePartnerCommand.class, commandRequest)
        .map(ResponseHelper::ok);
  }

  @GetMapping
  public Mono<Response<List<GetPartnerWebResponse>>> getAllPartner(AccessTokenParameter accessTokenParameter,
      @RequestParam(required = false) String partnerName, @RequestParam(defaultValue = "1") int page,
      @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "-createdDate") String sortBy) {
    GetAllPartnerCommandRequest commandRequest = GetAllPartnerCommandRequest.builder()
        .companyId(accessTokenParameter.getCompanyId())
        .name(partnerName)
        .pageable(PagingHelper.from(page, size, sortBy))
        .build();

    return executor.execute(GetAllPartnerCommand.class, commandRequest)
        .map(ResponseHelper::ok);
  }

  @GetMapping("/{id}")
  public Mono<Response<GetPartnerWebResponse>> getPartnerById(AccessTokenParameter accessTokenParameter,
      @PathVariable String id) {
    GetPartnerByIdCommandRequest commandRequest = GetPartnerByIdCommandRequest.builder()
        .companyId(accessTokenParameter.getCompanyId())
        .id(id)
        .build();

    return executor.execute(GetPartnerByIdCommand.class, commandRequest)
        .map(ResponseHelper::ok);
  }

  @PutMapping("/{id}")
  public Mono<Response<GetPartnerWebResponse>> updatePartnerById(AccessTokenParameter accessTokenParameter,
      @PathVariable String id, @RequestBody UpdatePartnerWebRequest request) {
    UpdatePartnerByIdCommandRequest commandRequest = UpdatePartnerByIdCommandRequest.builder()
        .companyId(accessTokenParameter.getCompanyId())
        .id(id)
        .name(request.getName())
        .description(request.getDescription())
        .build();

    return executor.execute(UpdatePartnerByIdCommand.class, commandRequest)
        .map(ResponseHelper::ok);
  }

  @DeleteMapping("/{id}")
  public Mono<Response<Void>> deletePartnerById(AccessTokenParameter accessTokenParameter, @PathVariable String id) {
    DeletePartnerByIdCommandRequest commandRequest = DeletePartnerByIdCommandRequest.builder()
        .companyId(accessTokenParameter.getCompanyId())
        .id(id)
        .build();

    return executor.execute(DeletePartnerByIdCommand.class, commandRequest)
        .map(ResponseHelper::ok);
  }
}

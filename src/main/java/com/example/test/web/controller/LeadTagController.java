package com.example.test.web.controller;

import com.example.test.command.leadTag.CreateLeadTagCommand;
import com.example.test.command.leadTag.GetAllLeadTagCommand;
import com.example.test.command.leadTag.GetLeadTagByIdCommand;
import com.example.test.command.model.leadTag.CreateLeadTagCommandRequest;
import com.example.test.command.model.leadTag.GetAllLeadTagCommandRequest;
import com.example.test.command.model.leadTag.GetLeadTagByIdCommandRequest;
import com.example.test.common.enums.RoleEnum;
import com.example.test.web.model.request.leadTag.CreateLeadTagWebRequest;
import com.example.test.web.model.resolver.AccessTokenParameter;
import com.example.test.web.model.response.leadTag.GetLeadTagWebResponse;
import com.example.test.web.security.MustAuthenticated;
import com.solusinegeri.command.helper.PagingHelper;
import com.solusinegeri.command.reactive.executor.CommandExecutor;
import com.solusinegeri.common.helper.ResponseHelper;
import com.solusinegeri.common.model.web.response.Response;
import com.solusinegeri.web.controller.reactive.BaseController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/crm/lead-tag")
@MustAuthenticated(userRole = {RoleEnum.sa})
public class LeadTagController extends BaseController {

  public LeadTagController(CommandExecutor executor) {
    super(executor);
  }

  @PostMapping
  public Mono<Response<GetLeadTagWebResponse>> createLeadTag(AccessTokenParameter accessTokenParameter,
      @RequestBody CreateLeadTagWebRequest request) {
    CreateLeadTagCommandRequest commandRequest = CreateLeadTagCommandRequest.builder()
        .companyId(accessTokenParameter.getCompanyId())
        .name(request.getName())
        .description(request.getDescription())
        .build();

    return executor.execute(CreateLeadTagCommand.class, commandRequest)
        .map(ResponseHelper::ok);
  }

  @GetMapping
  public Mono<Response<List<GetLeadTagWebResponse>>> getAllLeadTag(AccessTokenParameter accessTokenParameter,
      @RequestParam(required = false) String tagName, @RequestParam(defaultValue = "1") int page,
      @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "-createdDate") String sortBy) {
    GetAllLeadTagCommandRequest commandRequest = GetAllLeadTagCommandRequest.builder()
        .companyId(accessTokenParameter.getCompanyId())
        .name(tagName)
        .pageable(PagingHelper.from(page, size, sortBy))
        .build();

    return executor.execute(GetAllLeadTagCommand.class, commandRequest)
        .map(ResponseHelper::ok);
  }

  @GetMapping("/{id}")
  public Mono<Response<GetLeadTagWebResponse>> getLeadTagById(AccessTokenParameter accessTokenParameter,
      @PathVariable String id) {
    GetLeadTagByIdCommandRequest commandRequest = GetLeadTagByIdCommandRequest.builder()
        .companyId(accessTokenParameter.getCompanyId())
        .id(id)
        .build();

    return executor.execute(GetLeadTagByIdCommand.class, commandRequest)
        .map(ResponseHelper::ok);
  }
}

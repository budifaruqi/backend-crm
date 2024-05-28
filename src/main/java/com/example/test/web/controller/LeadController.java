package com.example.test.web.controller;

import com.example.test.command.lead.CreateLeadCommand;
import com.example.test.command.lead.GetAllLeadCommand;
import com.example.test.command.lead.GetLeadByIdCommand;
import com.example.test.command.model.lead.CreateLeadCommandRequest;
import com.example.test.command.model.lead.GetAllLeadCommandRequest;
import com.example.test.command.model.lead.GetLeadByIdCommandRequest;
import com.example.test.common.enums.LeadStatus;
import com.example.test.common.enums.RoleEnum;
import com.example.test.web.model.request.lead.CreateLeadWebRequest;
import com.example.test.web.model.resolver.AccessTokenParameter;
import com.example.test.web.model.response.lead.GetLeadWebResponse;
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
@RequestMapping("/crm/lead")
@MustAuthenticated(userRole = RoleEnum.sa)
public class LeadController extends BaseController {

  public LeadController(CommandExecutor executor) {
    super(executor);
  }

  @PostMapping
  public Mono<Response<Object>> createLeadByPotentialLeadId(AccessTokenParameter accessTokenParameter,
      @RequestBody CreateLeadWebRequest request) {
    CreateLeadCommandRequest commandRequest = CreateLeadCommandRequest.builder()
        .companyId(accessTokenParameter.getCompanyId())
        .potentialLeadId(request.getPotentialLeadId())
        .picName(request.getPicName())
        .picPhone(request.getPicPhone())
        .picEmail(request.getPicEmail())
        .description(request.getDescription())
        .leadTagIds(request.getLeadTagIds())
        .address(request.getAddress())
        .city(request.getCity())
        .province(request.getProvince())
        .gMapLink(request.getGMapLink())
        .potentialSize(request.getPotentialSize())
        .potentialRevenue(request.getPotentialRevenue())
        .facebook(request.getFacebook())
        .instagram(request.getInstagram())
        .reference(request.getReference())
        .build();

    return executor.execute(CreateLeadCommand.class, commandRequest)
        .map(ResponseHelper::ok);
  }

  @GetMapping
  public Mono<Response<List<GetLeadWebResponse>>> getAllLeadByCompanyId(AccessTokenParameter accessTokenParameter,
      @RequestParam(required = false) String leadName, @RequestParam(required = false) List<String> tagIds,
      @RequestParam(required = false) String partnerId, @RequestParam(required = false) String city,
      @RequestParam(required = false) String province, @RequestParam(required = false) String reference,
      @RequestParam(required = false) LeadStatus status, @RequestParam(required = false) Boolean isCustomer,
      @RequestParam(required = false) Boolean isLive, @RequestParam(required = false) Boolean isDormant,
      @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size,
      @RequestParam(defaultValue = "-createdDate") String sortBy) {
    GetAllLeadCommandRequest commandRequest = GetAllLeadCommandRequest.builder()
        .companyId(accessTokenParameter.getCompanyId())
        .leadName(leadName)
        .tagIds(tagIds)
        .partnerId(partnerId)
        .city(city)
        .province(province)
        .reference(reference)
        .status(status)
        .isCustomer(isCustomer)
        .isLive(isLive)
        .isDormant(isDormant)
        .pageable(PagingHelper.from(page, size, sortBy))
        .build();

    return executor.execute(GetAllLeadCommand.class, commandRequest)
        .map(ResponseHelper::ok);
  }

  @GetMapping("/{id}")
  public Mono<Response<GetLeadWebResponse>> getLeadByIdAndCompanyId(AccessTokenParameter accessTokenParameter,
      @PathVariable String id) {
    GetLeadByIdCommandRequest commandRequest = GetLeadByIdCommandRequest.builder()
        .companyId(accessTokenParameter.getCompanyId())
        .id(id)
        .build();

    return executor.execute(GetLeadByIdCommand.class, commandRequest)
        .map(ResponseHelper::ok);
  }
}

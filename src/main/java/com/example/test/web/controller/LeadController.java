package com.example.test.web.controller;

import com.example.test.command.lead.CreateLeadCommand;
import com.example.test.command.lead.DeleteLeadByIdCommand;
import com.example.test.command.lead.GetAllLeadCommand;
import com.example.test.command.lead.GetLeadByIdCommand;
import com.example.test.command.lead.UpdateLeadByIdCommand;
import com.example.test.command.lead.UpdateLeadStatusByIdCommand;
import com.example.test.command.model.lead.CreateLeadCommandRequest;
import com.example.test.command.model.lead.DeleteLeadByIdCommandRequest;
import com.example.test.command.model.lead.GetAllLeadCommandRequest;
import com.example.test.command.model.lead.GetLeadByIdCommandRequest;
import com.example.test.command.model.lead.UpdateLeadByIdCommandRequest;
import com.example.test.command.model.lead.UpdateLeadStatusByIdCommandRequest;
import com.example.test.common.constant.ResponseType;
import com.example.test.common.enums.LeadStatus;
import com.example.test.common.helper.response.MicroserviceResponse;
import com.example.test.common.helper.response.MicroserviceResponseHelper;
import com.example.test.web.model.request.lead.CreateLeadWebRequest;
import com.example.test.web.model.request.lead.UpdateLeadByIdWebRequest;
import com.example.test.web.model.resolver.AccessTokenParameter;
import com.example.test.web.model.response.lead.GetLeadWebResponse;
import com.example.test.web.security.MustAuthenticated;
import com.solusinegeri.command.helper.PagingHelper;
import com.solusinegeri.command.reactive.executor.CommandExecutor;
import com.solusinegeri.web.controller.reactive.BaseController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
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
@RequestMapping("/crm/lead")
public class LeadController extends BaseController {

  public LeadController(CommandExecutor executor) {
    super(executor);
  }

  @PostMapping
  @Operation(summary = "Create Lead",
      operationId = "create_lead",
      tags = "Lead Management",
      extensions = {@Extension(properties = {
          @ExtensionProperty(name = "x-credentialLocations", value = "[\"system\", \"company\"]", parseValue = true)})})
  @MustAuthenticated(operationId = "create_lead")
  public Mono<MicroserviceResponse<Object>> createLeadByPotentialLeadId(AccessTokenParameter accessTokenParameter,
      @RequestBody CreateLeadWebRequest request) {
    CreateLeadCommandRequest commandRequest = CreateLeadCommandRequest.builder()
        .companyGroupId(accessTokenParameter.getCompanyGroupId())
        .name(request.getName())
        .picName(request.getPicName())
        .picPhone(request.getPicPhone())
        .picEmail(request.getPicEmail())
        .description(request.getDescription())
        .address(request.getAddress())
        .city(request.getCity())
        .province(request.getProvince())
        .gMapLink(request.getGMapLink())
        .potentialSize(request.getPotentialSize())
        .potentialRevenue(request.getPotentialRevenue())
        .facebook(request.getFacebook())
        .instagram(request.getInstagram())
        .tags(request.getTags())
        .salesId(accessTokenParameter.getAccountId())
        .bankId(request.getBankId())
        .reference(request.getReference())
        .build();

    return executor.execute(CreateLeadCommand.class, commandRequest)
        .map(data -> MicroserviceResponseHelper.ok(data, ResponseType.SUCCESS_CREATE_LEAD));
  }

  @GetMapping
  @Operation(summary = "Get All Lead",
      operationId = "get_all_lead",
      tags = "Lead Management",
      extensions = {@Extension(properties = {
          @ExtensionProperty(name = "x-credentialLocations", value = "[\"system\", \"company\"]", parseValue = true)})})
  @MustAuthenticated(operationId = "get_all_lead")
  public Mono<MicroserviceResponse<List<GetLeadWebResponse>>> getAllLead(AccessTokenParameter accessTokenParameter,
      @RequestParam(required = false) String leadName, @RequestParam(required = false) List<String> tagIds,
      @RequestParam(required = false) String city, @RequestParam(required = false) String province,
      @RequestParam(required = false) String reference, @RequestParam(required = false) LeadStatus status,
      @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size,
      @RequestParam(defaultValue = "-createdDate") String sortBy) {
    GetAllLeadCommandRequest commandRequest = GetAllLeadCommandRequest.builder()
        .companyGroupId(accessTokenParameter.getCompanyGroupId())
        .leadName(leadName)
        .tagIds(tagIds)
        .city(city)
        .province(province)
        .reference(reference)
        .status(status)
        .pageable(PagingHelper.from(page, size, sortBy))
        .build();

    return executor.execute(GetAllLeadCommand.class, commandRequest)
        .map(data -> MicroserviceResponseHelper.ok(data, ResponseType.SUCCESS_GET_LEAD));
  }

  @GetMapping("/{id}")
  @Operation(summary = "Get Lead by Id",
      operationId = "get_lead_by_id",
      tags = "Lead Management",
      extensions = {@Extension(properties = {
          @ExtensionProperty(name = "x-credentialLocations", value = "[\"system\", \"company\"]", parseValue = true)})})
  @MustAuthenticated(operationId = "get_lead_by_id")
  public Mono<MicroserviceResponse<GetLeadWebResponse>> getLeadById(AccessTokenParameter accessTokenParameter,
      @PathVariable String id) {
    GetLeadByIdCommandRequest commandRequest = GetLeadByIdCommandRequest.builder()
        .companyGroupId(accessTokenParameter.getCompanyGroupId())
        .id(id)
        .build();

    return executor.execute(GetLeadByIdCommand.class, commandRequest)
        .map(data -> MicroserviceResponseHelper.ok(data, ResponseType.SUCCESS_GET_LEAD));
  }

  @PutMapping("/{id}")
  @Operation(summary = "Update Lead by Id",
      operationId = "update_lead_by_id",
      tags = "Lead Management",
      extensions = {@Extension(properties = {
          @ExtensionProperty(name = "x-credentialLocations", value = "[\"system\", \"company\"]", parseValue = true)})})
  @MustAuthenticated(operationId = "update_lead_by_id")
  public Mono<MicroserviceResponse<Object>> updateLeadById(AccessTokenParameter accessTokenParameter,
      @PathVariable String id, @RequestBody UpdateLeadByIdWebRequest request) {
    UpdateLeadByIdCommandRequest commandRequest = UpdateLeadByIdCommandRequest.builder()
        .companyGroupId(accessTokenParameter.getCompanyGroupId())
        .id(id)
        .picName(request.getPicName())
        .picPhone(request.getPicPhone())
        .picEmail(request.getPicEmail())
        .description(request.getDescription())
        .address(request.getAddress())
        .city(request.getCity())
        .province(request.getProvince())
        .gMapLink(request.getGMapLink())
        .potentialSize(request.getPotentialSize())
        .potentialRevenue(request.getPotentialRevenue())
        .facebook(request.getFacebook())
        .instagram(request.getInstagram())
        .tags(request.getTags())
        .salesId(request.getSalesId())
        .bankId(request.getBankId())
        .reference(request.getReference())
        .build();

    return executor.execute(UpdateLeadByIdCommand.class, commandRequest)
        .map(data -> MicroserviceResponseHelper.ok(data, ResponseType.SUCCESS_UPDATE_LEAD));
  }

  @PutMapping("/status/{id}")
  @Operation(summary = "Update Lead Status by Id",
      operationId = "update_lead_status_by_id",
      tags = "Lead Management",
      extensions = {@Extension(properties = {
          @ExtensionProperty(name = "x-credentialLocations", value = "[\"system\", \"company\"]", parseValue = true)})})
  @MustAuthenticated(operationId = "update_lead_status_by_id")
  public Mono<MicroserviceResponse<Object>> updateLeadStatusById(AccessTokenParameter accessTokenParameter,
      @PathVariable String id, @RequestParam LeadStatus status) {
    UpdateLeadStatusByIdCommandRequest commandRequest = UpdateLeadStatusByIdCommandRequest.builder()
        .companyGroupId(accessTokenParameter.getCompanyGroupId())
        .id(id)
        .status(status)
        .build();

    return executor.execute(UpdateLeadStatusByIdCommand.class, commandRequest)
        .map(data -> MicroserviceResponseHelper.ok(data, ResponseType.SUCCESS_UPDATE_LEAD));
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "Delete Lead by Id",
      operationId = "delete_lead_by_id",
      tags = "Lead Management",
      extensions = {@Extension(properties = {
          @ExtensionProperty(name = "x-credentialLocations", value = "[\"system\", \"company\"]", parseValue = true)})})
  @MustAuthenticated(operationId = "delete_lead_by_id")
  public Mono<MicroserviceResponse<Void>> deleteLeadById(AccessTokenParameter accessTokenParameter,
      @PathVariable String id) {
    DeleteLeadByIdCommandRequest commandRequest = DeleteLeadByIdCommandRequest.builder()
        .companyGroupId(accessTokenParameter.getCompanyGroupId())
        .id(id)
        .build();

    return executor.execute(DeleteLeadByIdCommand.class, commandRequest)
        .map(data -> MicroserviceResponseHelper.ok(data, ResponseType.SUCCESS_DELETE_LEAD));
  }
}

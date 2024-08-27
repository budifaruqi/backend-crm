package com.example.test.web.controller;

import com.example.test.command.leadTag.CreateLeadTagCommand;
import com.example.test.command.leadTag.DeleteLeadTagByIdCommand;
import com.example.test.command.leadTag.GetAllLeadTagCommand;
import com.example.test.command.leadTag.GetLeadTagByIdCommand;
import com.example.test.command.leadTag.UpdateLeadTagByIdCommand;
import com.example.test.command.model.leadTag.CreateLeadTagCommandRequest;
import com.example.test.command.model.leadTag.DeleteLeadTagByIdCommandRequest;
import com.example.test.command.model.leadTag.GetAllLeadTagCommandRequest;
import com.example.test.command.model.leadTag.GetLeadTagByIdCommandRequest;
import com.example.test.command.model.leadTag.UpdateLeadTagByIdCommandRequest;
import com.example.test.common.constant.ResponseType;
import com.example.test.common.helper.response.MicroserviceResponse;
import com.example.test.common.helper.response.MicroserviceResponseHelper;
import com.example.test.web.model.request.leadTag.CreateLeadTagWebRequest;
import com.example.test.web.model.request.leadTag.UpdateLeadTagWebRequest;
import com.example.test.web.model.resolver.AccessTokenParameter;
import com.example.test.web.model.response.leadTag.GetLeadTagWebResponse;
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
@RequestMapping("/crm/lead-tag")
public class LeadTagController extends BaseController {

  public LeadTagController(CommandExecutor executor) {
    super(executor);
  }

  @PostMapping
  @Operation(summary = "Create Lead Tag",
      operationId = "create_lead_tag",
      tags = "Lead Tag Management",
      extensions = {@Extension(properties = {
          @ExtensionProperty(name = "x-credentialLocations", value = "[\"system\", \"company\"]", parseValue = true)})})
  @MustAuthenticated(operationId = "create_lead_tag")
  public Mono<MicroserviceResponse<GetLeadTagWebResponse>> createLeadTag(AccessTokenParameter accessTokenParameter,
      @RequestBody CreateLeadTagWebRequest request) {
    CreateLeadTagCommandRequest commandRequest = CreateLeadTagCommandRequest.builder()
        .companyGroupId(accessTokenParameter.getCompanyGroupId())
        .name(request.getName())
        .description(request.getDescription())
        .build();

    return executor.execute(CreateLeadTagCommand.class, commandRequest)
        .map(data -> MicroserviceResponseHelper.ok(data, ResponseType.SUCCESS_CREATE_LEAD_TAG));
  }

  @GetMapping
  @Operation(summary = "Get All Lead Tag",
      operationId = "get_all_lead_tag",
      tags = "Lead Tag Management",
      extensions = {@Extension(properties = {
          @ExtensionProperty(name = "x-credentialLocations", value = "[\"system\", \"company\"]", parseValue = true)})})
  @MustAuthenticated(operationId = "get_all_lead_tag")
  public Mono<MicroserviceResponse<List<GetLeadTagWebResponse>>> getAllLeadTag(
      AccessTokenParameter accessTokenParameter, @RequestParam(required = false) String tagName,
      @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size,
      @RequestParam(defaultValue = "-createdDate") String sortBy) {
    GetAllLeadTagCommandRequest commandRequest = GetAllLeadTagCommandRequest.builder()
        .companyGroupId(accessTokenParameter.getCompanyGroupId())
        .name(tagName)
        .pageable(PagingHelper.from(page, size, sortBy))
        .build();

    return executor.execute(GetAllLeadTagCommand.class, commandRequest)
        .map(data -> MicroserviceResponseHelper.ok(data, ResponseType.SUCCESS_GET_LEAD_TAG));
  }

  @GetMapping("/{id}")
  @Operation(summary = "Get Lead Tag by Id",
      operationId = "get_lead_tag_by_id",
      tags = "Lead Tag Management",
      extensions = {@Extension(properties = {
          @ExtensionProperty(name = "x-credentialLocations", value = "[\"system\", \"company\"]", parseValue = true)})})
  @MustAuthenticated(operationId = "get_lead_tag_by_id")
  public Mono<MicroserviceResponse<GetLeadTagWebResponse>> getLeadTagById(AccessTokenParameter accessTokenParameter,
      @PathVariable String id) {
    GetLeadTagByIdCommandRequest commandRequest = GetLeadTagByIdCommandRequest.builder()
        .companyGroupId(accessTokenParameter.getCompanyGroupId())
        .id(id)
        .build();

    return executor.execute(GetLeadTagByIdCommand.class, commandRequest)
        .map(data -> MicroserviceResponseHelper.ok(data, ResponseType.SUCCESS_GET_LEAD_TAG));
  }

  @PutMapping("/{id}")
  @Operation(summary = "Update Lead Tag by Id",
      operationId = "update_lead_tag_by_id",
      tags = "Lead Tag Management",
      extensions = {@Extension(properties = {
          @ExtensionProperty(name = "x-credentialLocations", value = "[\"system\", \"company\"]", parseValue = true)})})
  @MustAuthenticated(operationId = "update_lead_tag_by_id")
  public Mono<MicroserviceResponse<GetLeadTagWebResponse>> updateLeadTagById(AccessTokenParameter accessTokenParameter,
      @PathVariable String id, @RequestBody UpdateLeadTagWebRequest request) {
    UpdateLeadTagByIdCommandRequest commandRequest = UpdateLeadTagByIdCommandRequest.builder()
        .companyGroupId(accessTokenParameter.getCompanyGroupId())
        .id(id)
        .name(request.getName())
        .description(request.getDescription())
        .build();

    return executor.execute(UpdateLeadTagByIdCommand.class, commandRequest)
        .map(data -> MicroserviceResponseHelper.ok(data, ResponseType.SUCCESS_UPDATE_LEAD_TAG));
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "Delete Lead Tag by Id",
      operationId = "delete_lead_tag_by_id",
      tags = "Lead Tag Management",
      extensions = {@Extension(properties = {
          @ExtensionProperty(name = "x-credentialLocations", value = "[\"system\", \"company\"]", parseValue = true)})})
  @MustAuthenticated(operationId = "delete_lead_tag_by_id")
  public Mono<MicroserviceResponse<Void>> deleteLeadTagById(AccessTokenParameter accessTokenParameter,
      @PathVariable String id) {
    DeleteLeadTagByIdCommandRequest commandRequest = DeleteLeadTagByIdCommandRequest.builder()
        .companyGroupId(accessTokenParameter.getCompanyGroupId())
        .id(id)
        .build();

    return executor.execute(DeleteLeadTagByIdCommand.class, commandRequest)
        .map(data -> MicroserviceResponseHelper.ok(data, ResponseType.SUCCESS_DELETE_LEAD_TAG));
  }
}

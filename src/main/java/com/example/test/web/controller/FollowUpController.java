package com.example.test.web.controller;

import com.example.test.command.followUp.CreateFollowUpCommand;
import com.example.test.command.followUp.DeleteFollowUpByIdCommand;
import com.example.test.command.followUp.GetAllFollowUpCommand;
import com.example.test.command.followUp.GetFollowUpByIdCommand;
import com.example.test.command.followUp.UpdateFollowUpByIdCommand;
import com.example.test.command.model.followUp.CreateFollowUpCommandRequest;
import com.example.test.command.model.followUp.DeleteFollowUpByIdCommandRequest;
import com.example.test.command.model.followUp.GetAllFollowUpCommandRequest;
import com.example.test.command.model.followUp.GetFollowUpByIdCommandRequest;
import com.example.test.command.model.followUp.UpdateFollowUpByIdCommandRequest;
import com.example.test.common.constant.ResponseType;
import com.example.test.common.enums.FollowUpActivity;
import com.example.test.common.enums.FollowUpStatus;
import com.example.test.common.helper.response.MicroserviceResponse;
import com.example.test.common.helper.response.MicroserviceResponseHelper;
import com.example.test.web.model.request.followUp.CreateFollowUpWebRequest;
import com.example.test.web.model.request.followUp.UpdateFollowUpByIdWebRequest;
import com.example.test.web.model.resolver.AccessTokenParameter;
import com.example.test.web.model.response.followUp.GetFollowUpWebResponse;
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
@RequestMapping("/crm/follow-up")
public class FollowUpController extends BaseController {

  public FollowUpController(CommandExecutor executor) {
    super(executor);
  }

  @PostMapping
  @Operation(summary = "Create Follow Up",
      operationId = "create_follow_up",
      tags = "Follow Up Management",
      extensions = {@Extension(properties = {
          @ExtensionProperty(name = "x-credentialLocations", value = "[\"system\", \"company\"]", parseValue = true)})})
  @MustAuthenticated(operationId = "create_follow_up")
  public Mono<MicroserviceResponse<Object>> createFollowUp(AccessTokenParameter accessTokenParameter,
      @RequestBody CreateFollowUpWebRequest request) {
    CreateFollowUpCommandRequest commandRequest = CreateFollowUpCommandRequest.builder()
        .companyGroupId(accessTokenParameter.getCompanyGroupId())
        .leadId(request.getLeadId())
        .salesId(accessTokenParameter.getAccountId())
        .activity(request.getActivity())
        .status(request.getStatus())
        .note(request.getNote())
        .build();

    return executor.execute(CreateFollowUpCommand.class, commandRequest)
        .map(data -> MicroserviceResponseHelper.ok(data, ResponseType.SUCCESS_CREATE_FOLLOW_UP));
  }

  @GetMapping
  @Operation(summary = "Get All Follow Up",
      operationId = "get_all_follow_up",
      tags = "Follow Up Management",
      extensions = {@Extension(properties = {
          @ExtensionProperty(name = "x-credentialLocations", value = "[\"system\", \"company\"]", parseValue = true)})})
  @MustAuthenticated(operationId = "get_all_follow_up")
  public Mono<MicroserviceResponse<List<GetFollowUpWebResponse>>> getAllFollowUp(
      AccessTokenParameter accessTokenParameter, @RequestParam(required = false) String leadId,
      @RequestParam(required = false) FollowUpActivity activity, @RequestParam(required = false) FollowUpStatus status,
      @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size,
      @RequestParam(defaultValue = "-createdDate") String sortBy) {
    GetAllFollowUpCommandRequest commandRequest = GetAllFollowUpCommandRequest.builder()
        .companyGroupId(accessTokenParameter.getCompanyGroupId())
        .salesId(accessTokenParameter.getAccountId())
        .leadId(leadId)
        .activity(activity)
        .status(status)
        .pageable(PagingHelper.from(page, size, sortBy))
        .build();

    return executor.execute(GetAllFollowUpCommand.class, commandRequest)
        .map(data -> MicroserviceResponseHelper.ok(data, ResponseType.SUCCESS_GET_FOLLOW_UP));
  }

  @GetMapping("/{id}")
  @Operation(summary = "Get Follow Up by Id",
      operationId = "get_follow_up_by_id",
      tags = "Follow Up Management",
      extensions = {@Extension(properties = {
          @ExtensionProperty(name = "x-credentialLocations", value = "[\"system\", \"company\"]", parseValue = true)})})
  @MustAuthenticated(operationId = "get_follow_up_by_id")
  public Mono<MicroserviceResponse<GetFollowUpWebResponse>> getFollowUpById(AccessTokenParameter accessTokenParameter,
      @PathVariable String id) {
    GetFollowUpByIdCommandRequest commandRequest = GetFollowUpByIdCommandRequest.builder()
        .companyGroupId(accessTokenParameter.getCompanyGroupId())
        .salesId(accessTokenParameter.getAccountId())
        .id(id)
        .build();

    return executor.execute(GetFollowUpByIdCommand.class, commandRequest)
        .map(data -> MicroserviceResponseHelper.ok(data, ResponseType.SUCCESS_GET_FOLLOW_UP));
  }

  @PutMapping("/{id}")
  @Operation(summary = "Update Follow Up by Id",
      operationId = "update_follow_up_by_id",
      tags = "Follow Up Management",
      extensions = {@Extension(properties = {
          @ExtensionProperty(name = "x-credentialLocations", value = "[\"system\", \"company\"]", parseValue = true)})})
  @MustAuthenticated(operationId = "update_follow_up_by_id")
  public Mono<MicroserviceResponse<Object>> updateFollowUpById(AccessTokenParameter accessTokenParameter,
      @PathVariable String id, @RequestBody UpdateFollowUpByIdWebRequest request) {
    UpdateFollowUpByIdCommandRequest commandRequest = UpdateFollowUpByIdCommandRequest.builder()
        .companyGroupId(accessTokenParameter.getCompanyGroupId())
        .id(id)
        .salesId(accessTokenParameter.getAccountId())
        .leadId(request.getLeadId())
        .activity(request.getActivity())
        .status(request.getStatus())
        .note(request.getNote())
        .build();

    return executor.execute(UpdateFollowUpByIdCommand.class, commandRequest)
        .map(data -> MicroserviceResponseHelper.ok(data, ResponseType.SUCCESS_UPDATE_FOLLOW_UP));
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "Delete Follow Up by Id",
      operationId = "delete_follow_up_by_id",
      tags = "Follow Up Management",
      extensions = {@Extension(properties = {
          @ExtensionProperty(name = "x-credentialLocations", value = "[\"system\", \"company\"]", parseValue = true)})})
  @MustAuthenticated(operationId = "delete_follow_up_by_id")
  public Mono<MicroserviceResponse<Void>> deleteFollowUpById(AccessTokenParameter accessTokenParameter,
      @PathVariable String id) {
    DeleteFollowUpByIdCommandRequest commandRequest = DeleteFollowUpByIdCommandRequest.builder()
        .companyGroupId(accessTokenParameter.getCompanyGroupId())
        .salesId(accessTokenParameter.getAccountId())
        .id(id)
        .build();

    return executor.execute(DeleteFollowUpByIdCommand.class, commandRequest)
        .map(data -> MicroserviceResponseHelper.ok(data, ResponseType.SUCCESS_DELETE_FOLLOW_UP));
  }
}

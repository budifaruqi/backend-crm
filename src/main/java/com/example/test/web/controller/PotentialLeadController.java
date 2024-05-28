package com.example.test.web.controller;

import com.example.test.command.model.potentialLead.CreatePotentialLeadCommandRequest;
import com.example.test.command.model.potentialLead.DeletePotentialLeadCommandRequest;
import com.example.test.command.model.potentialLead.GetAllPotentialLeadCommandRequest;
import com.example.test.command.model.potentialLead.GetPotentialLeadByIdCommandRequest;
import com.example.test.command.model.potentialLead.UpdatePotentialLeadCommandRequest;
import com.example.test.command.model.potentialLead.UpdatePotentialLeadStatusByIdCommandRequest;
import com.example.test.command.potentialLead.CreatePotentialLeadCommand;
import com.example.test.command.potentialLead.DeletePotentialLeadCommand;
import com.example.test.command.potentialLead.GetAllPotentialLeadCommand;
import com.example.test.command.potentialLead.GetPotentialLeadByIdCommand;
import com.example.test.command.potentialLead.UpdatePotentialLeadCommand;
import com.example.test.command.potentialLead.UpdatePotentialLeadStatusByIdCommand;
import com.example.test.common.enums.PotentialLeadStatus;
import com.example.test.common.enums.RoleEnum;
import com.example.test.web.model.request.potentialLead.CreatePotentialLeadWebRequest;
import com.example.test.web.model.request.potentialLead.UpdatePotentialLeadWebRequest;
import com.example.test.web.model.resolver.AccessTokenParameter;
import com.example.test.web.model.response.potentialLead.GetPotentialLeadWebResponse;
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
@RequestMapping("/crm/potential-lead")
@MustAuthenticated(userRole = {RoleEnum.sa})
public class PotentialLeadController extends BaseController {

  public PotentialLeadController(CommandExecutor executor) {
    super(executor);
  }

  @PostMapping
  public Mono<Response<Object>> createPotentialLead(AccessTokenParameter accessTokenParameter,
      @RequestBody CreatePotentialLeadWebRequest request) {
    CreatePotentialLeadCommandRequest commandRequest = CreatePotentialLeadCommandRequest.builder()
        .companyId(accessTokenParameter.getCompanyId())
        .partnerId(request.getPartnerId())
        .name(request.getName())
        .phone(request.getPhone())
        .email(request.getEmail())
        .requirementList(request.getRequirementList())
        .build();

    return executor.execute(CreatePotentialLeadCommand.class, commandRequest)
        .map(ResponseHelper::ok);
  }

  @GetMapping
  public Mono<Response<List<GetPotentialLeadWebResponse>>> getAllPotentialLead(
      AccessTokenParameter accessTokenParameter, @RequestParam(required = false) String leadName,
      @RequestParam(required = false) String partnerId, @RequestParam(required = false) PotentialLeadStatus status,
      @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size,
      @RequestParam(defaultValue = "-createdDate") String sortBy) {
    GetAllPotentialLeadCommandRequest commandRequest = GetAllPotentialLeadCommandRequest.builder()
        .companyId(accessTokenParameter.getCompanyId())
        .name(leadName)
        .partnerId(partnerId)
        .status(status)
        .pageable(PagingHelper.from(page, size, sortBy))
        .build();

    return executor.execute(GetAllPotentialLeadCommand.class, commandRequest)
        .map(ResponseHelper::ok);
  }

  @GetMapping("/{id}")
  public Mono<Response<GetPotentialLeadWebResponse>> getPotentialLeadById(AccessTokenParameter accessTokenParameter,
      @PathVariable String id) {
    GetPotentialLeadByIdCommandRequest commandRequest = GetPotentialLeadByIdCommandRequest.builder()
        .companyId(accessTokenParameter.getCompanyId())
        .id(id)
        .build();

    return executor.execute(GetPotentialLeadByIdCommand.class, commandRequest)
        .map(ResponseHelper::ok);
  }

  @PutMapping("/{id}")
  public Mono<Response<GetPotentialLeadWebResponse>> updatePotentialLeadById(AccessTokenParameter accessTokenParameter,
      @PathVariable String id, @RequestBody UpdatePotentialLeadWebRequest request) {
    UpdatePotentialLeadCommandRequest commandRequest = UpdatePotentialLeadCommandRequest.builder()
        .companyId(accessTokenParameter.getCompanyId())
        .id(id)
        .partnerId(request.getPartnerId())
        .name(request.getName())
        .phone(request.getPhone())
        .email(request.getEmail())
        .requirementList(request.getRequirementList())
        .build();

    return executor.execute(UpdatePotentialLeadCommand.class, commandRequest)
        .map(ResponseHelper::ok);
  }

  @PutMapping("/status/{id}")
  public Mono<Response<GetPotentialLeadWebResponse>> updatePotentialLeadStatusById(
      AccessTokenParameter accessTokenParameter, @PathVariable String id, @RequestParam PotentialLeadStatus status) {
    UpdatePotentialLeadStatusByIdCommandRequest commandRequest = UpdatePotentialLeadStatusByIdCommandRequest.builder()
        .companyId(accessTokenParameter.getCompanyId())
        .id(id)
        .status(status)
        .build();

    return executor.execute(UpdatePotentialLeadStatusByIdCommand.class, commandRequest)
        .map(ResponseHelper::ok);
  }

  @DeleteMapping("/{id}")
  public Mono<Response<Object>> deletePotentialLeadById(AccessTokenParameter accessTokenParameter,
      @PathVariable String id) {
    DeletePotentialLeadCommandRequest commandRequest = DeletePotentialLeadCommandRequest.builder()
        .companyId(accessTokenParameter.getCompanyId())
        .id(id)
        .build();

    return executor.execute(DeletePotentialLeadCommand.class, commandRequest)
        .map(ResponseHelper::ok);
  }
}

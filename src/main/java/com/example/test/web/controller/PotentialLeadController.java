package com.example.test.web.controller;

import com.example.test.common.enums.RoleEnum;
import com.example.test.web.security.MustAuthenticated;
import com.solusinegeri.command.reactive.executor.CommandExecutor;
import com.solusinegeri.web.controller.reactive.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/crm/potential-lead")
@MustAuthenticated(userRole = {RoleEnum.sa})
public class PotentialLeadController extends BaseController {

  public PotentialLeadController(CommandExecutor executor) {
    super(executor);
  }

  //  @PostMapping
  //  public Mono<MicroserviceResponse<Object>> createPotentialLead(AccessTokenParameter accessTokenParameter,
  //      @RequestBody CreatePotentialLeadWebRequest request) {
  //    CreatePotentialLeadCommandRequest commandRequest = CreatePotentialLeadCommandRequest.builder()
  //        .companyGroupId(accessTokenParameter.getCompanyGroupId())
  //        .name(request.getName())
  //        .phone(request.getPhone())
  //        .email(request.getEmail())
  //        .requirementList(request.getRequirementList())
  //        .build();
  //
  //    return executor.execute(CreatePotentialLeadCommand.class, commandRequest)
  //        .map(data -> MicroserviceResponseHelper.ok(data, ResponseType.SUCCESS_CREATE_POTENTIAL_LEAD));
  //  }
  //
  //  @GetMapping
  //  public Mono<MicroserviceResponse<List<GetPotentialLeadWebResponse>>> getAllPotentialLead(
  //      AccessTokenParameter accessTokenParameter, @RequestParam(required = false) String leadName,
  //      @RequestParam(required = false) PotentialLeadStatus status, @RequestParam(defaultValue = "1") int page,
  //      @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "-createdDate") String sortBy) {
  //    GetAllPotentialLeadCommandRequest commandRequest = GetAllPotentialLeadCommandRequest.builder()
  //        .companyGroupId(accessTokenParameter.getCompanyGroupId())
  //        .name(leadName)
  //        .status(status)
  //        .pageable(PagingHelper.from(page, size, sortBy))
  //        .build();
  //
  //    return executor.execute(GetAllPotentialLeadCommand.class, commandRequest)
  //        .map(data -> MicroserviceResponseHelper.ok(data, ResponseType.SUCCESS_GET_POTENTIAL_LEAD));
  //  }
  //
  //  @GetMapping("/{id}")
  //  public Mono<MicroserviceResponse<GetPotentialLeadWebResponse>> getPotentialLeadById(
  //      AccessTokenParameter accessTokenParameter, @PathVariable String id) {
  //    GetPotentialLeadByIdCommandRequest commandRequest = GetPotentialLeadByIdCommandRequest.builder()
  //        .companyGroupId(accessTokenParameter.getCompanyGroupId())
  //        .id(id)
  //        .build();
  //
  //    return executor.execute(GetPotentialLeadByIdCommand.class, commandRequest)
  //        .map(data -> MicroserviceResponseHelper.ok(data, ResponseType.SUCCESS_GET_POTENTIAL_LEAD));
  //  }
  //
  //  @PutMapping("/{id}")
  //  public Mono<MicroserviceResponse<GetPotentialLeadWebResponse>> updatePotentialLeadById(
  //      AccessTokenParameter accessTokenParameter, @PathVariable String id,
  //      @RequestBody UpdatePotentialLeadWebRequest request) {
  //    UpdatePotentialLeadCommandRequest commandRequest = UpdatePotentialLeadCommandRequest.builder()
  //        .companyGroupId(accessTokenParameter.getCompanyGroupId())
  //        .id(id)
  //        .name(request.getName())
  //        .phone(request.getPhone())
  //        .email(request.getEmail())
  //        .requirementList(request.getRequirementList())
  //        .build();
  //
  //    return executor.execute(UpdatePotentialLeadCommand.class, commandRequest)
  //        .map(data -> MicroserviceResponseHelper.ok(data, ResponseType.SUCCESS_UPDATE_POTENTIAL_LEAD));
  //  }
  //
  //  @PutMapping("/status/{id}")
  //  public Mono<MicroserviceResponse<GetPotentialLeadWebResponse>> updatePotentialLeadStatusById(
  //      AccessTokenParameter accessTokenParameter, @PathVariable String id, @RequestParam PotentialLeadStatus status) {
  //    UpdatePotentialLeadStatusByIdCommandRequest commandRequest = UpdatePotentialLeadStatusByIdCommandRequest.builder()
  //        .companyGroupId(accessTokenParameter.getCompanyGroupId())
  //        .id(id)
  //        .status(status)
  //        .build();
  //
  //    return executor.execute(UpdatePotentialLeadStatusByIdCommand.class, commandRequest)
  //        .map(data -> MicroserviceResponseHelper.ok(data, ResponseType.SUCCESS_UPDATE_POTENTIAL_LEAD));
  //  }
  //
  //  @DeleteMapping("/{id}")
  //  public Mono<MicroserviceResponse<Object>> deletePotentialLeadById(AccessTokenParameter accessTokenParameter,
  //      @PathVariable String id) {
  //    DeletePotentialLeadCommandRequest commandRequest = DeletePotentialLeadCommandRequest.builder()
  //        .companyGroupId(accessTokenParameter.getCompanyGroupId())
  //        .id(id)
  //        .build();
  //
  //    return executor.execute(DeletePotentialLeadCommand.class, commandRequest)
  //        .map(data -> MicroserviceResponseHelper.ok(data, ResponseType.SUCCESS_DELETE_POTENTIAL_LEAD));
  //  }
}

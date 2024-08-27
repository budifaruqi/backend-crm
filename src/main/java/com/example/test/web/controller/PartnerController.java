package com.example.test.web.controller;

import com.solusinegeri.command.reactive.executor.CommandExecutor;
import com.solusinegeri.web.controller.reactive.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/crm/partner")

public class PartnerController extends BaseController {

  public PartnerController(CommandExecutor executor) {
    super(executor);
  }

  //  @PostMapping
  //  public Mono<Response<GetPartnerWebResponse>> createPartner(AccessTokenParameter accessTokenParameter,
  //      @RequestBody CreatePartnerWebRequest request) {
  //    CreatePartnerCommandRequest commandRequest = CreatePartnerCommandRequest.builder()
  //        .companyId(accessTokenParameter.getCompanyId())
  //        .name(request.getName())
  //        .description(request.getDescription())
  //        .build();
  //
  //    return executor.execute(CreatePartnerCommand.class, commandRequest)
  //        .map(ResponseHelper::ok);
  //  }
  //
  //  @GetMapping
  //  public Mono<Response<List<GetPartnerWebResponse>>> getAllPartner(AccessTokenParameter accessTokenParameter,
  //      @RequestParam(required = false) String partnerName, @RequestParam(defaultValue = "1") int page,
  //      @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "-createdDate") String sortBy) {
  //    GetAllPartnerCommandRequest commandRequest = GetAllPartnerCommandRequest.builder()
  //        .companyId(accessTokenParameter.getCompanyId())
  //        .name(partnerName)
  //        .pageable(PagingHelper.from(page, size, sortBy))
  //        .build();
  //
  //    return executor.execute(GetAllPartnerCommand.class, commandRequest)
  //        .map(ResponseHelper::ok);
  //  }
  //
  //  @GetMapping("/{id}")
  //  public Mono<Response<GetPartnerWebResponse>> getPartnerById(AccessTokenParameter accessTokenParameter,
  //      @PathVariable String id) {
  //    GetPartnerByIdCommandRequest commandRequest = GetPartnerByIdCommandRequest.builder()
  //        .companyId(accessTokenParameter.getCompanyId())
  //        .id(id)
  //        .build();
  //
  //    return executor.execute(GetPartnerByIdCommand.class, commandRequest)
  //        .map(ResponseHelper::ok);
  //  }
  //
  //  @PutMapping("/{id}")
  //  public Mono<Response<GetPartnerWebResponse>> updatePartnerById(AccessTokenParameter accessTokenParameter,
  //      @PathVariable String id, @RequestBody UpdatePartnerWebRequest request) {
  //    UpdatePartnerByIdCommandRequest commandRequest = UpdatePartnerByIdCommandRequest.builder()
  //        .companyId(accessTokenParameter.getCompanyId())
  //        .id(id)
  //        .name(request.getName())
  //        .description(request.getDescription())
  //        .build();
  //
  //    return executor.execute(UpdatePartnerByIdCommand.class, commandRequest)
  //        .map(ResponseHelper::ok);
  //  }
  //
  //  @DeleteMapping("/{id}")
  //  public Mono<Response<Void>> deletePartnerById(AccessTokenParameter accessTokenParameter, @PathVariable String id) {
  //    DeletePartnerByIdCommandRequest commandRequest = DeletePartnerByIdCommandRequest.builder()
  //        .companyId(accessTokenParameter.getCompanyId())
  //        .id(id)
  //        .build();
  //
  //    return executor.execute(DeletePartnerByIdCommand.class, commandRequest)
  //        .map(ResponseHelper::ok);
  //  }
}

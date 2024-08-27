package com.example.test.command.impl.externalUser;

import com.example.test.client.MembershipClient;
import com.example.test.client.model.request.CreateExternalAccountClientRequest;
import com.example.test.client.model.response.CreateExternalAccountClientResponse;
import com.example.test.command.externalUser.CreateExternalUserCommand;
import com.example.test.command.model.externalUser.CreateExternalUserCommandRequest;
import com.example.test.common.constant.ErrorCode;
import com.example.test.common.enums.AccountType;
import com.example.test.common.enums.LeadStatus;
import com.example.test.common.helper.response.exception.MicroserviceValidationException;
import com.example.test.repository.BankRepository;
import com.example.test.repository.ExternalUserRepository;
import com.example.test.repository.LeadRepository;
import com.example.test.repository.model.ExternalUser;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.Objects;

@Service
public class CreateExternalUserCommandImpl implements CreateExternalUserCommand {

  private final LeadRepository leadRepository;

  private final BankRepository bankRepository;

  private final ExternalUserRepository externalUserRepository;

  private final MembershipClient membershipClient;

  public CreateExternalUserCommandImpl(LeadRepository leadRepository, BankRepository bankRepository,
      ExternalUserRepository externalUserRepository, MembershipClient membershipClient) {
    this.leadRepository = leadRepository;
    this.bankRepository = bankRepository;
    this.externalUserRepository = externalUserRepository;
    this.membershipClient = membershipClient;
  }

  @Override
  public Mono<Object> execute(CreateExternalUserCommandRequest request) {
    return Mono.defer(() -> checkRequest(request))
        .flatMap(s -> toClient(request))
        .map(response -> toUser(response, request))
        .flatMap(externalUserRepository::save);
  }

  private ExternalUser toUser(CreateExternalAccountClientResponse response, CreateExternalUserCommandRequest request) {
    return ExternalUser.builder()
        .id(response.get_id())
        .companyGroupId(request.getCompanyGroupId())
        .externalId(request.getExternalId())
        .type(request.getType())
        .name(response.getName())
        .username(response.getUsername())
        .email(response.getEmail())
        .phone(response.getPhone())
        .build();
  }

  private Mono<ExternalUser> checkRequest(CreateExternalUserCommandRequest request) {
    return Mono.defer(() -> checkExternalId(request))
        .filter(s -> s)
        .switchIfEmpty(Mono.error(new MicroserviceValidationException(ErrorCode.WRONG_EXTERNAL_ID)))
        .flatMap(s -> checkName(request));
  }

  private Mono<Boolean> checkExternalId(CreateExternalUserCommandRequest request) {
    if (request.getType() == AccountType.BANK) {
      return bankRepository.existsByDeletedFalseAndCompanyGroupIdAndId(request.getCompanyGroupId(),
              request.getExternalId())
          .switchIfEmpty(Mono.error(new MicroserviceValidationException(ErrorCode.BANK_NOT_EXIST)));
    }
    return leadRepository.existsByDeletedFalseAndCompanyGroupIdAndIdAndStatus(request.getCompanyGroupId(),
            request.getExternalId(), LeadStatus.WON)
        .switchIfEmpty(Mono.error(new MicroserviceValidationException(ErrorCode.LEAD_NOT_EXIST)));
  }

  private Mono<ExternalUser> checkName(CreateExternalUserCommandRequest request) {
    return externalUserRepository.findByDeletedFalseAndCompanyGroupIdAndUsername(request.getCompanyGroupId(),
            request.getUsername())
        .switchIfEmpty(Mono.fromSupplier(() -> ExternalUser.builder()
            .build()))
        .filter(s -> !Objects.equals(s.getUsername(), request.getUsername()))
        .switchIfEmpty(Mono.error(new MicroserviceValidationException(ErrorCode.USERNAME_ALREADY_USED)));
  }

  private Mono<CreateExternalAccountClientResponse> toClient(CreateExternalUserCommandRequest request) {
    return membershipClient.createExternalUser(createExternalUserClientRequest(request))
        .map(s -> {
          System.out.println(s);
          return s.getData();
        });
  }

  private CreateExternalAccountClientRequest createExternalUserClientRequest(CreateExternalUserCommandRequest request) {
    return CreateExternalAccountClientRequest.builder()
        .name(request.getName())
        .username(request.getUsername())
        .email(request.getEmail())
        .phone(request.getPhone())
        .password(request.getPassword())
        .companyCategoryId(request.getCompanyGroupId())
        .features(Collections.singletonList(request.getType()
            .toString()))
        .createdBy(request.getSalesId())
        .build();
  }
}

package com.example.test.command.impl.externalUser;

import com.example.test.command.externalUser.CreateExternalUserCommand;
import com.example.test.command.model.externalUser.CreateExternalUserCommandRequest;
import com.example.test.repository.BankRepository;
import com.example.test.repository.ExternalUserRepository;
import com.example.test.repository.LeadRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class CreateExternalUserCommandImpl implements CreateExternalUserCommand {

  private final LeadRepository leadRepository;

  private final BankRepository bankRepository;

  private final ExternalUserRepository externalUserRepository;

  public CreateExternalUserCommandImpl(LeadRepository leadRepository, BankRepository bankRepository,
      ExternalUserRepository externalUserRepository) {
    this.leadRepository = leadRepository;
    this.bankRepository = bankRepository;
    this.externalUserRepository = externalUserRepository;
  }

  @Override
  public Mono<Object> execute(CreateExternalUserCommandRequest request) {
    return null;
  }
}

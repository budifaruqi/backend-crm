package com.example.test.command.bank;

import com.example.test.command.model.bank.GetAllBankCommandRequest;
import com.example.test.web.model.response.bank.GetBankWebResponse;
import com.solusinegeri.command.reactive.Command;
import org.springframework.data.domain.Page;

public interface GetAllBankCommand extends Command<GetAllBankCommandRequest, Page<GetBankWebResponse>> {}

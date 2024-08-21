package com.example.test.command.bank;

import com.example.test.command.model.bank.GetBankByIdCommandRequest;
import com.example.test.web.model.response.bank.GetBankWebResponse;
import com.solusinegeri.command.reactive.Command;

public interface GetBankByIdCommand extends Command<GetBankByIdCommandRequest, GetBankWebResponse> {}

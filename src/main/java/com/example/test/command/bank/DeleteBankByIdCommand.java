package com.example.test.command.bank;

import com.example.test.command.model.bank.DeleteBankByIdCommandRequest;
import com.solusinegeri.command.reactive.Command;

public interface DeleteBankByIdCommand extends Command<DeleteBankByIdCommandRequest, Void> {}

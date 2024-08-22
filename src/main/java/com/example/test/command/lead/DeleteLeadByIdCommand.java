package com.example.test.command.lead;

import com.example.test.command.model.lead.DeleteLeadByIdCommandRequest;
import com.solusinegeri.command.reactive.Command;

public interface DeleteLeadByIdCommand extends Command<DeleteLeadByIdCommandRequest, Void> {}

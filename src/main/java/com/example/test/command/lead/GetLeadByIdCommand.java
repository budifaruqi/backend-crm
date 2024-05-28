package com.example.test.command.lead;

import com.example.test.command.model.lead.GetLeadByIdCommandRequest;
import com.example.test.web.model.response.lead.GetLeadWebResponse;
import com.solusinegeri.command.reactive.Command;

public interface GetLeadByIdCommand extends Command<GetLeadByIdCommandRequest, GetLeadWebResponse> {}

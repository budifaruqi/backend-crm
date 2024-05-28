package com.example.test.command.potentialLead;

import com.example.test.command.model.potentialLead.GetPotentialLeadByIdCommandRequest;
import com.example.test.web.model.response.potentialLead.GetPotentialLeadWebResponse;
import com.solusinegeri.command.reactive.Command;

public interface GetPotentialLeadByIdCommand
    extends Command<GetPotentialLeadByIdCommandRequest, GetPotentialLeadWebResponse> {}

package com.example.test.command.potentialLead;

import com.example.test.command.model.potentialLead.UpdatePotentialLeadStatusByIdCommandRequest;
import com.example.test.web.model.response.potentialLead.GetPotentialLeadWebResponse;
import com.solusinegeri.command.reactive.Command;

public interface UpdatePotentialLeadStatusByIdCommand
    extends Command<UpdatePotentialLeadStatusByIdCommandRequest, GetPotentialLeadWebResponse> {}

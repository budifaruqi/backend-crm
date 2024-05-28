package com.example.test.command.potentialLead;

import com.example.test.command.model.potentialLead.UpdatePotentialLeadCommandRequest;
import com.example.test.web.model.response.potentialLead.GetPotentialLeadWebResponse;
import com.solusinegeri.command.reactive.Command;

public interface UpdatePotentialLeadCommand
    extends Command<UpdatePotentialLeadCommandRequest, GetPotentialLeadWebResponse> {}

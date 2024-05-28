package com.example.test.command.potentialLead;

import com.example.test.command.model.potentialLead.GetAllPotentialLeadCommandRequest;
import com.example.test.web.model.response.potentialLead.GetPotentialLeadWebResponse;
import com.solusinegeri.command.reactive.Command;
import org.springframework.data.domain.Page;

public interface GetAllPotentialLeadCommand
    extends Command<GetAllPotentialLeadCommandRequest, Page<GetPotentialLeadWebResponse>> {}

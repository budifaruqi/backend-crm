package com.example.test.command.lead;

import com.example.test.command.model.lead.GetAllLeadCommandRequest;
import com.example.test.web.model.response.lead.GetLeadWebResponse;
import com.solusinegeri.command.reactive.Command;
import org.springframework.data.domain.Page;

public interface GetAllLeadCommand extends Command<GetAllLeadCommandRequest, Page<GetLeadWebResponse>> {}

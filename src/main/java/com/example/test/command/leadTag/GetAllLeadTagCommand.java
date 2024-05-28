package com.example.test.command.leadTag;

import com.example.test.command.model.leadTag.GetAllLeadTagCommandRequest;
import com.example.test.web.model.response.leadTag.GetLeadTagWebResponse;
import com.solusinegeri.command.reactive.Command;
import org.springframework.data.domain.Page;

public interface GetAllLeadTagCommand extends Command<GetAllLeadTagCommandRequest, Page<GetLeadTagWebResponse>> {}

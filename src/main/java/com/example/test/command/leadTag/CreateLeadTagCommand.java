package com.example.test.command.leadTag;

import com.example.test.command.model.leadTag.CreateLeadTagCommandRequest;
import com.example.test.web.model.response.leadTag.GetLeadTagWebResponse;
import com.solusinegeri.command.reactive.Command;

public interface CreateLeadTagCommand extends Command<CreateLeadTagCommandRequest, GetLeadTagWebResponse> {}
